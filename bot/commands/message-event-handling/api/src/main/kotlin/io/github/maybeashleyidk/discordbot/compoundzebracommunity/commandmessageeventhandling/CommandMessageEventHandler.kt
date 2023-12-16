package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commandmessageeventhandling

import net.dv8tion.jda.api.events.GenericEvent

public interface CommandMessageEventHandler {

	/**
	 * Returns a boolean that indicates whether the event was consumed or not.
	 */
	public suspend fun handleEvent(event: GenericEvent): Boolean
}
