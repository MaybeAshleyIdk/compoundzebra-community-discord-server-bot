package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.genericeventhandler.GenericEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling.PollEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnBeforeShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling.ShutdownEventHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.events.GenericEvent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

public class MainEventListenerImpl(
	private val logger: Logger,
	private val shutdownEventHandler: ShutdownEventHandler,
	shutdownCallbackRegistry: ShutdownCallbackRegistry,
	messageEventHandlerMediator: MessageEventHandlerMediator,
	pollEventHandler: PollEventHandler,
	privateMessageEventHandler: PrivateMessageEventHandler,
) : MainEventListener {

	private companion object {

		const val INCOMING_EVENTS_THREAD_POOL_TERMINATION_TIMEOUT_MS: Long = 2500

		const val EVENT_HANDLERS_THREAD_POOL_TERMINATION_TIMEOUT_MS: Long = 7500
	}

	private val otherEventHandlers: Set<GenericEventHandler> =
		setOf(
			messageEventHandlerMediator,
			pollEventHandler,
			privateMessageEventHandler,
		)

	@Volatile
	private var isShuttingDownOrIsShutDown: Boolean = false

	private val incomingEventsThreadPool: ExecutorService = Executors.newCachedThreadPool()
	private val incomingEventsCoroutineScope: CoroutineScope =
		CoroutineScope(this.incomingEventsThreadPool.asCoroutineDispatcher() + SupervisorJob())

	private val eventHandlersThreadPool: ExecutorService = Executors.newCachedThreadPool()
	private val eventHandlersCoroutineContext: CoroutineContext = this.eventHandlersThreadPool.asCoroutineDispatcher()

	init {
		val onBeforeShutdownCallback =
			OnBeforeShutdownCallback {
				this.isShuttingDownOrIsShutDown = true
			}

		val onAfterShutdownCallback = OnAfterShutdownCallback(this::shutdownThreadPools)

		shutdownCallbackRegistry.registerCallback(onBeforeShutdownCallback)
		shutdownCallbackRegistry.registerCallback(onAfterShutdownCallback)
	}

	override fun onEvent(event: GenericEvent) {
		if (this.isShuttingDownOrIsShutDown) {
			return
		}

		this.incomingEventsCoroutineScope.launch {
			this@MainEventListenerImpl.handleEvent(event)
		}
	}

	private suspend fun handleEvent(event: GenericEvent) {
		val status: ShutdownEventHandler.Status = this.shutdownEventHandler.handleEvent(event)
		when (status) {
			ShutdownEventHandler.Status.STILL_RUNNING -> Unit
			ShutdownEventHandler.Status.SHUTTING_DOWN -> return
		}

		withContext(this.eventHandlersCoroutineContext) {
			this@MainEventListenerImpl.otherEventHandlers
				.map { eventHandler: GenericEventHandler ->
					this@withContext.launch {
						this@MainEventListenerImpl.executeEventHandler(eventHandler, event)
					}
				}
				.joinAll()
		}
	}

	private suspend fun executeEventHandler(eventHandler: GenericEventHandler, event: GenericEvent) {
		try {
			eventHandler.handleEvent(event)
		} catch (e: CancellationException) {
			throw e
		} catch (e: Throwable) {
			this.logger.logError(e, "Event handler $eventHandler threw an exception")

			if (e !is Exception) {
				throw e
			}
		}
	}

	private fun shutdownThreadPools() {
		this.incomingEventsThreadPool.shutdown()
		this.eventHandlersThreadPool.shutdown()

		@Suppress("RemoveRedundantQualifierName")
		val incomingEventsThreadPoolTerminated: Boolean = this.incomingEventsThreadPool
			.awaitTermination(
				MainEventListenerImpl.INCOMING_EVENTS_THREAD_POOL_TERMINATION_TIMEOUT_MS,
				TimeUnit.MILLISECONDS,
			)

		@Suppress("RemoveRedundantQualifierName")
		val eventHandlersThreadPoolTerminated: Boolean =
			this.eventHandlersThreadPool.awaitTermination(
				MainEventListenerImpl.EVENT_HANDLERS_THREAD_POOL_TERMINATION_TIMEOUT_MS,
				TimeUnit.MILLISECONDS,
			)

		if (!incomingEventsThreadPoolTerminated) this.incomingEventsThreadPool.shutdownNow()
		if (!eventHandlersThreadPoolTerminated) this.eventHandlersThreadPool.shutdownNow()
	}
}
