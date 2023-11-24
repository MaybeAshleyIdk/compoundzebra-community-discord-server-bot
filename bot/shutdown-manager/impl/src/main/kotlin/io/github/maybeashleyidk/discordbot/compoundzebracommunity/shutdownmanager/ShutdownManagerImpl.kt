package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnBeforeShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager.ShutdownManager.EventHandlingResultStatus
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.session.ShutdownEvent
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import net.dv8tion.jda.api.JDA as Jda

// the JDA instance is injected via a Provider because otherwise we'd have a circular dependency.
// i'm not happy with this fix, bob. not. happy.
// but it works, so for now it stays

@Singleton
public class ShutdownManagerImpl @Inject constructor(
	private val jdaProvider: Provider<Jda>,
	private val logger: Logger,
) : ShutdownManager {

	private enum class State {
		RUNNING,
		SHUTTING_DOWN,
		SHUT_DOWN,
	}

	private val jda: Jda by lazy(this.jdaProvider::get)

	private val shutdownMonitor: Any = Any()

	private val onBeforeShutdownCallbacks: MutableList<OnBeforeShutdownCallback> = ArrayList()
	private val onAfterShutdownCallbacks: MutableList<OnAfterShutdownCallback> = ArrayList()

	private val currentStateMonitor: Any = Any()
	private var currentState: State = State.RUNNING

	private val shutdownRequested: AtomicBoolean = AtomicBoolean(false)
	private val shutdownRequestSemaphore: Semaphore = Semaphore(1)
		.also(Semaphore::acquire)

	init {
		val jvmShutdownHookThread =
			Thread {
				this.shutDown()
			}

		val shutdownAwaitThread =
			Thread(
				{
					this.jda.awaitShutdown()
					this.shutDown()
				},
				"shutdown-await",
			)

		val shutdownRequestAwaitThread =
			Thread(
				{
					this.shutdownRequestSemaphore.acquire()
					this.fulfillShutdownRequest()
				},
				"shutdown-request-await",
			)

		Runtime.getRuntime().addShutdownHook(jvmShutdownHookThread)

		shutdownAwaitThread.start()
		shutdownRequestAwaitThread.start()
	}

	// region callback registry

	override fun registerShutdownCallback(callback: OnBeforeShutdownCallback) {
		val shouldExecuteNow: Boolean =
			synchronized(this.onBeforeShutdownCallbacks) {
				val currentState: State =
					synchronized(this.currentStateMonitor) {
						this.currentState
					}

				val shouldExecuteNow: Boolean =
					when (currentState) {
						State.RUNNING -> false
						State.SHUTTING_DOWN,
						State.SHUT_DOWN,
						-> true
					}

				if (!shouldExecuteNow) {
					this.onBeforeShutdownCallbacks.add(callback)
				}

				shouldExecuteNow
			}

		if (shouldExecuteNow) {
			callback.onBeforeShutdown()
		}
	}

	override fun registerShutdownCallback(callback: OnAfterShutdownCallback) {
		val shouldExecuteNow: Boolean =
			synchronized(this.onAfterShutdownCallbacks) {
				val currentState: State =
					synchronized(this.currentStateMonitor) {
						this.currentState
					}

				val shouldExecuteNow: Boolean =
					when (currentState) {
						State.RUNNING,
						State.SHUTTING_DOWN,
						-> false

						State.SHUT_DOWN -> true
					}

				if (!shouldExecuteNow) {
					this.onAfterShutdownCallbacks.add(callback)
				}

				shouldExecuteNow
			}

		if (shouldExecuteNow) {
			callback.onAfterShutdown()
		}
	}

	override fun unregisterShutdownCallback(callback: OnAfterShutdownCallback) {
		synchronized(this.onAfterShutdownCallbacks) {
			this.onAfterShutdownCallbacks
				.asReversed()
				.remove(callback)
		}
	}

	override fun unregisterShutdownCallback(callback: OnBeforeShutdownCallback) {
		synchronized(this.onBeforeShutdownCallbacks) {
			this.onBeforeShutdownCallbacks
				.asReversed()
				.remove(callback)
		}
	}

	// endregion

	override fun handleShutdownEvent(event: GenericEvent): EventHandlingResultStatus {
		if (event !is ShutdownEvent) {
			return EventHandlingResultStatus.STILL_RUNNING
		}

		this.requestShutdown()

		return EventHandlingResultStatus.SHUTTING_DOWN
	}

	override fun requestShutdown() {
		if (!(this.shutdownRequested.compareAndSet(false, true))) {
			// shutdown already requested; do nothing
			return
		}

		this.shutdownRequestSemaphore.release()
	}

	private fun fulfillShutdownRequest() {
		synchronized(this.shutdownMonitor) {
			check(this.shutdownRequested.get()) {
				"Shutdown has not been requested"
			}

			val onBeforeShutdownCallbacks: List<OnBeforeShutdownCallback> =
				synchronized(this.onBeforeShutdownCallbacks) {
					val copy: List<OnBeforeShutdownCallback> = this.onBeforeShutdownCallbacks.toList()

					this.onBeforeShutdownCallbacks.clear()

					copy
				}

			for (callback: OnBeforeShutdownCallback in onBeforeShutdownCallbacks) {
				this.executeOnBeforeShutdownCallback(callback)
			}

			this.shutDownUnsynchronized()
		}
	}

	private fun shutDown() {
		synchronized(this.shutdownMonitor) {
			this.shutDownUnsynchronized()
		}
	}

	private fun shutDownUnsynchronized() {
		val onBeforeShutdownCallbacks: List<OnBeforeShutdownCallback> =
			synchronized(this.onBeforeShutdownCallbacks) {
				val isRunning: Boolean =
					synchronized(this.currentStateMonitor) currentState@{
						val isRunning: Boolean =
							when (val currentState: State = this.currentState) {
								State.RUNNING -> true
								State.SHUTTING_DOWN -> error("Current state must not be $currentState")
								State.SHUT_DOWN -> false
							}

						if (isRunning) {
							this.currentState = State.SHUTTING_DOWN
						}

						isRunning
					}

				if (!isRunning) {
					return@synchronized null
				}

				val copy: List<OnBeforeShutdownCallback> = this.onBeforeShutdownCallbacks.toList()

				this.onBeforeShutdownCallbacks.clear()

				copy
			}
				?: return

		for (callback: OnBeforeShutdownCallback in onBeforeShutdownCallbacks) {
			this.executeOnBeforeShutdownCallback(callback)
		}

		shutDownJda(this.jda, this.logger)

		val (previousState: State, onAfterShutdownCallbacks: List<OnAfterShutdownCallback>) =
			synchronized(this.onAfterShutdownCallbacks) {
				val previousState: State =
					synchronized(this.currentStateMonitor) {
						val previousState: State = this.currentState

						this.currentState = State.SHUT_DOWN

						previousState
					}

				val onAfterShutdownCallbacks: List<OnAfterShutdownCallback> =
					synchronized(this.onAfterShutdownCallbacks) {
						val copy: List<OnAfterShutdownCallback> = this.onAfterShutdownCallbacks.toList()

						this.onAfterShutdownCallbacks.clear()

						copy
					}

				previousState to onAfterShutdownCallbacks
			}

		when (previousState) {
			State.RUNNING,
			State.SHUT_DOWN,
			-> {
				val msg = "Expected the shutdown state after the shutdown has finished to be ${State.SHUTTING_DOWN}" +
					", but it was $previousState"
				this.logger.logWarning(msg)
			}

			State.SHUTTING_DOWN -> Unit
		}

		for (callback: OnAfterShutdownCallback in onAfterShutdownCallbacks) {
			try {
				callback.onAfterShutdown()
			} catch (e: Throwable) {
				this.logger.logError(e, "OnAfterShutdownCallback threw an exception")

				if (e !is Exception) {
					throw e
				}
			}
		}
	}

	private fun executeOnBeforeShutdownCallback(callback: OnBeforeShutdownCallback) {
		try {
			callback.onBeforeShutdown()
		} catch (e: Throwable) {
			this.logger.logError(e, "OnBeforeShutdownCallback threw an exception")

			if (e !is Exception) {
				throw e
			}
		}
	}
}
