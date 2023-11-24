package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler.EventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening.PollEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowneventhandler.ShutdownEventHandler
import net.dv8tion.jda.api.events.GenericEvent
import javax.inject.Inject

public class MainEventListenerImpl @Inject constructor(
	private val logger: Logger,
	private val shutdownEventHandler: ShutdownEventHandler,
	messageEventHandlerMediator: MessageEventHandlerMediator,
	pollEventListener: PollEventListener,
	privateMessageEventHandler: PrivateMessageEventHandler,
) : MainEventListener {

	private val otherEventHandlers: Set<EventHandler> =
		setOf(
			messageEventHandlerMediator,
			pollEventListener,
			privateMessageEventHandler,
		)

	override fun onEvent(event: GenericEvent) {
		val status: ShutdownEventHandler.Status = this.shutdownEventHandler.handleEvent(event)
		when (status) {
			ShutdownEventHandler.Status.STILL_RUNNING -> Unit
			ShutdownEventHandler.Status.SHUTTING_DOWN -> return
		}

		for (eventHandler: EventHandler in this.otherEventHandlers) {
			try {
				eventHandler.handleEvent(event)
			} catch (e: Throwable) {
				this.logger.logError(e, "Event handler $eventHandler threw an exception")

				if (e !is Exception) {
					throw e
				}
			}
		}
	}
}
