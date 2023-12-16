package io.github.maybeashleyidk.discordbot.compoundzebracommunity.genericeventhandler

import net.dv8tion.jda.api.events.GenericEvent

public interface GenericEventHandler {

	public suspend fun handleEvent(event: GenericEvent)
}
