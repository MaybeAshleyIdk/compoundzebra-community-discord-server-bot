package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistenermediator

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages.ConditionalMessageEventHandler
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import javax.inject.Inject

public class EventListenerMediator @Inject constructor(
	private val commandEventHandler: CommandEventHandler,
	private val conditionalMessageEventHandler: ConditionalMessageEventHandler,
) : EventListener {

	override fun onEvent(event: GenericEvent) {
		// TODO: change this from runBlocking
		runBlocking {
			val consumed: Boolean = this@EventListenerMediator.commandEventHandler.handleEvent(event)

			if (consumed) {
				return@runBlocking
			}

			this@EventListenerMediator.conditionalMessageEventHandler.handleEvent(event)
		}
	}
}
