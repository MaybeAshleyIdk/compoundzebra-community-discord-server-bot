package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler.EventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation.MessageEventHandlerMediator
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening.PollEventListener
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling.PrivateMessageEventHandler
import net.dv8tion.jda.api.events.GenericEvent
import javax.inject.Inject

public class MainEventListenerImpl @Inject constructor(
	private val logger: Logger,
	messageEventHandlerMediator: MessageEventHandlerMediator,
	pollEventListener: PollEventListener,
	privateMessageEventHandler: PrivateMessageEventHandler,
) : MainEventListener {

	private val eventHandlers: Set<EventHandler> =
		setOf(
			messageEventHandlerMediator,
			pollEventListener,
			privateMessageEventHandler,
		)

	override fun onEvent(event: GenericEvent) {
		for (eventHandler: EventHandler in this.eventHandlers) {
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
