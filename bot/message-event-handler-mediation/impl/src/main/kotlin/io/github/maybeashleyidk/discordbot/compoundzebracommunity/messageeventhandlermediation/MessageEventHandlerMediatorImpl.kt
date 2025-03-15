package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.CommandMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import net.dv8tion.jda.api.events.GenericEvent

public class MessageEventHandlerMediatorImpl(
	private val commandMessageEventHandler: CommandMessageEventHandler,
	private val conditionalMessageEventHandler: ConditionalMessageEventHandler,
) : MessageEventHandlerMediator {

	override suspend fun handleEvent(event: GenericEvent): EventHandlingResult {
		@Suppress("MoveVariableDeclarationIntoWhen")
		val result: EventHandlingResult = this.commandMessageEventHandler.handleEvent(event)

		when (result) {
			EventHandlingResult.NotHandled -> Unit
			EventHandlingResult.Handled -> return result
		}

		return this.conditionalMessageEventHandler.handleEvent(event)
	}
}
