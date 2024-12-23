package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnBeforeShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager.EventHandlingResultStatus
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVal
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesatomic.AtomicVar
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.session.ShutdownEvent
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Supplier
import net.dv8tion.jda.api.JDA as Jda

// the JDA instance is injected via a Supplier because otherwise we'd have a circular dependency.
// i'm not happy with this fix, bob. not. happy.
// but it works, so for now it stays

public class ShutdownManagerImpl(
	private val jdaSupplier: Supplier<Jda>,
	private val logger: Logger,
) : ShutdownManager {

	private enum class State {
		RUNNING,
		SHUTTING_DOWN,
		SHUT_DOWN,
	}

	private val jda: Jda by lazy(this.jdaSupplier::get)

	private val shutdownMutex: Mutex = Mutex()

	private val onBeforeShutdownCallbacks: AtomicVal<MutableList<OnBeforeShutdownCallback>> = AtomicVal(ArrayList())
	private val onAfterShutdownCallbacks: AtomicVal<MutableList<OnAfterShutdownCallback>> = AtomicVal(ArrayList())

	private val currentState: AtomicVar<State> = AtomicVar(State.RUNNING)

	private val shutdownRequested: AtomicBoolean = AtomicBoolean(false)
	private val shutdownRequestSemaphore: Semaphore = Semaphore(1, acquiredPermits = 1)

	init {
		val jvmShutdownHookThread =
			Thread {
				runBlocking {
					this@ShutdownManagerImpl.shutDown()
				}
			}

		// TODO: runBlocking inside a Thread? there has to be a better way to do this

		val shutdownAwaitThread =
			Thread(
				{
					this.jda.awaitShutdown()

					runBlocking {
						this@ShutdownManagerImpl.shutDown()
					}
				},
				"shutdown-await",
			)

		val shutdownRequestAwaitThread =
			Thread(
				{
					runBlocking {
						this@ShutdownManagerImpl.shutdownRequestSemaphore.acquire()

						this@ShutdownManagerImpl.fulfillShutdownRequest()
					}
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
			this.onBeforeShutdownCallbacks.visitBlocking { list: MutableList<OnBeforeShutdownCallback> ->
				val currentState: State = this.currentState.get()

				val shouldExecuteNow: Boolean =
					when (currentState) {
						State.RUNNING -> false
						State.SHUTTING_DOWN,
						State.SHUT_DOWN,
						-> true
					}

				if (!shouldExecuteNow) {
					list.add(callback)
				}

				shouldExecuteNow
			}

		if (shouldExecuteNow) {
			callback.onBeforeShutdown()
		}
	}

	override fun registerShutdownCallback(callback: OnAfterShutdownCallback) {
		val shouldExecuteNow: Boolean =
			this.onAfterShutdownCallbacks.visitBlocking { list: MutableList<OnAfterShutdownCallback> ->
				val currentState: State = this.currentState.get()

				val shouldExecuteNow: Boolean =
					when (currentState) {
						State.RUNNING,
						State.SHUTTING_DOWN,
						-> false

						State.SHUT_DOWN -> true
					}

				if (!shouldExecuteNow) {
					list.add(callback)
				}

				shouldExecuteNow
			}

		if (shouldExecuteNow) {
			callback.onAfterShutdown()
		}
	}

	override fun unregisterShutdownCallback(callback: OnAfterShutdownCallback) {
		this.onAfterShutdownCallbacks.visitBlocking { list: MutableList<OnAfterShutdownCallback> ->
			list
				.asReversed()
				.remove(callback)
		}
	}

	override fun unregisterShutdownCallback(callback: OnBeforeShutdownCallback) {
		this.onBeforeShutdownCallbacks.visitBlocking { list: MutableList<OnBeforeShutdownCallback> ->
			list
				.asReversed()
				.remove(callback)
		}
	}

	// endregion

	override suspend fun handleShutdownEvent(event: GenericEvent): EventHandlingResultStatus {
		if (event !is ShutdownEvent) {
			return EventHandlingResultStatus.STILL_RUNNING
		}

		this.requestShutdown()

		return EventHandlingResultStatus.SHUTTING_DOWN
	}

	override fun requestShutdown() {
		this.logger.logInfo("Requesting shutdown...")

		if (!(this.shutdownRequested.compareAndSet(false, true))) {
			// shutdown already requested; do nothing
			return
		}

		this.shutdownRequestSemaphore.release()
	}

	private suspend fun fulfillShutdownRequest() {
		this.shutdownMutex.withLock {
			check(this.shutdownRequested.get()) {
				"Shutdown has not been requested"
			}

			val onBeforeShutdownCallbacks: List<OnBeforeShutdownCallback> =
				this.onBeforeShutdownCallbacks.visit { onBeforeShutdownCallbacks: MutableList<OnBeforeShutdownCallback> ->
					val copy: List<OnBeforeShutdownCallback> = onBeforeShutdownCallbacks.toList()

					onBeforeShutdownCallbacks.clear()

					copy
				}

			for (callback: OnBeforeShutdownCallback in onBeforeShutdownCallbacks) {
				this.executeOnBeforeShutdownCallback(callback)
			}

			this.shutDownWithoutLock()
		}
	}

	private suspend fun shutDown() {
		this.shutdownMutex.withLock {
			this.shutDownWithoutLock()
		}
	}

	private suspend fun shutDownWithoutLock() {
		val onBeforeShutdownCallbacks: List<OnBeforeShutdownCallback> =
			this.onBeforeShutdownCallbacks.visit { onBeforeShutdownCallbacks: MutableList<OnBeforeShutdownCallback> ->
				val isRunning: Boolean =
					this.currentState.visit { currentStateHandle: AtomicVar.Handle<State> ->
						val isRunning: Boolean =
							when (val currentState: State = currentStateHandle.get()) {
								State.RUNNING -> true
								State.SHUTTING_DOWN -> error("Current state must not be $currentState")
								State.SHUT_DOWN -> false
							}

						if (isRunning) {
							currentStateHandle.set(State.SHUTTING_DOWN)
						}

						isRunning
					}

				if (!isRunning) {
					return@visit null
				}

				val copy: List<OnBeforeShutdownCallback> = onBeforeShutdownCallbacks.toList()

				onBeforeShutdownCallbacks.clear()

				copy
			}
				?: return

		for (callback: OnBeforeShutdownCallback in onBeforeShutdownCallbacks) {
			this.executeOnBeforeShutdownCallback(callback)
		}

		shutDownJda(this.jda, this.logger)

		val (previousState: State, onAfterShutdownCallbacks: List<OnAfterShutdownCallback>) =
			this.onAfterShutdownCallbacks.visit { onAfterShutdownCallbacks: MutableList<OnAfterShutdownCallback> ->
				val previousState: State = this.currentState.getAndSet(State.SHUT_DOWN)

				val copy: List<OnAfterShutdownCallback> = onAfterShutdownCallbacks.toList()
				onAfterShutdownCallbacks.clear()

				previousState to copy
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

private fun <T, R> AtomicVal<T>.visitBlocking(block: suspend (T) -> R): R {
	return runBlocking {
		this@visitBlocking.visit(block)
	}
}
