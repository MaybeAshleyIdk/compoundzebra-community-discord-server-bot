package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandler
import net.dv8tion.jda.api.events.GenericEvent

public class MessageEventHandlerMediatorImpl(
	private val commandMessageEventHandler: CommandMessageEventHandler,
	private val conditionalMessageEventHandler: ConditionalMessageEventHandler,
) : MessageEventHandlerMediator {

	override suspend fun handleEvent(event: GenericEvent) {
		val consumed: Boolean = this.commandMessageEventHandler.handleEvent(event)

		if (consumed) {
			return
		}

		this.conditionalMessageEventHandler.handleEvent(event)
	}
}
