package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling.CommandMessageEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling.ConditionalMessageEventHandler
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.GenericEvent
import javax.inject.Inject

public class MessageEventHandlerMediatorImpl @Inject constructor(
	private val commandMessageEventHandler: CommandMessageEventHandler,
	private val conditionalMessageEventHandler: ConditionalMessageEventHandler,
) : MessageEventHandlerMediator {

	override fun handleEvent(event: GenericEvent) {
		// TODO: change this from runBlocking
		runBlocking {
			val consumed: Boolean = this@MessageEventHandlerMediatorImpl.commandMessageEventHandler.handleEvent(event)

			if (consumed) {
				return@runBlocking
			}

			this@MessageEventHandlerMediatorImpl.conditionalMessageEventHandler.handleEvent(event)
		}
	}
}
