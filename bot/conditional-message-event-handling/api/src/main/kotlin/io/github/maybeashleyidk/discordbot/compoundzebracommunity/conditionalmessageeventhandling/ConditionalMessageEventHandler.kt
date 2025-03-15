package io.github.maybeashleyidk.discordbot.compoundzebracommunity.conditionalmessageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import net.dv8tion.jda.api.events.GenericEvent

public interface ConditionalMessageEventHandler {

	public suspend fun handleEvent(event: GenericEvent): EventHandlingResult
}
