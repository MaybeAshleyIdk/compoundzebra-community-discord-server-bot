package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessages

import net.dv8tion.jda.api.events.GenericEvent

public interface ConditionalMessageEventHandler {

	public suspend fun handleEvent(event: GenericEvent)
}
