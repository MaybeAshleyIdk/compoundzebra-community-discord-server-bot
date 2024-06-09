package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandler
import kotlinx.coroutines.coroutineScope
import net.dv8tion.jda.api.events.GenericEvent
import javax.inject.Inject

public class MessageEventHandlerMediatorImpl @Inject constructor(
	private val commandMessageEventHandler: CommandMessageEventHandler,
	private val conditionalMessageEventHandler: ConditionalMessageEventHandler,
) : MessageEventHandlerMediator {

	override suspend fun handleEvent(event: GenericEvent) {
		coroutineScope {
			val consumed: Boolean = this@MessageEventHandlerMediatorImpl.commandMessageEventHandler.handleEvent(event)

			if (consumed) {
				return@coroutineScope
			}

			this@MessageEventHandlerMediatorImpl.conditionalMessageEventHandler.handleEvent(event)
		}
	}
}
