package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler

import net.dv8tion.jda.api.events.GenericEvent

public interface EventHandler {

	public suspend fun handleEvent(event: GenericEvent)
}
