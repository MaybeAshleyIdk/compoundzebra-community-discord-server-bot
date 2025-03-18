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
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.events.GatewayPingEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.StatusChangeEvent
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.http.HttpRequestEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.reflect.KClass

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
			LogUninterestingEventTypeHandler,
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

		val results: List<EventHandlingResult?> =
			withContext(this.eventHandlersCoroutineContext) {
				this@MainEventListenerImpl.otherEventHandlers
					.map { eventHandler: GenericEventHandler ->
						this@withContext.async {
							this@MainEventListenerImpl.executeEventHandler(eventHandler, event)
						}
					}
					.awaitAll()
			}

		if (EventHandlingResult.Handled !in results) {
			this.logger.logInfo("The event $event was not handled by any event handler")
		}
	}

	private suspend fun executeEventHandler(
		eventHandler: GenericEventHandler,
		event: GenericEvent,
	): EventHandlingResult? {
		return try {
			eventHandler.handleEvent(event)
		} catch (e: CancellationException) {
			throw e
		} catch (e: Throwable) {
			this.logger.logError(e, "Event handler $eventHandler threw an exception trying to handle the event $event")

			if (e !is Exception) {
				throw e
			}

			null
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

private object LogUninterestingEventTypeHandler : GenericEventHandler {

	private val uninterestingEventClasses: List<Class<out GenericEvent>> =
		setOf(
			StatusChangeEvent::class,
			HttpRequestEvent::class,
			GatewayPingEvent::class,
			GuildReadyEvent::class,
			ReadyEvent::class,
		).map(KClass<out GenericEvent>::java)

	override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		return when (event.javaClass in this.uninterestingEventClasses) {
			true -> EventHandlingResult.Handled
			false -> EventHandlingResult.NotHandled
		}
	}
}
