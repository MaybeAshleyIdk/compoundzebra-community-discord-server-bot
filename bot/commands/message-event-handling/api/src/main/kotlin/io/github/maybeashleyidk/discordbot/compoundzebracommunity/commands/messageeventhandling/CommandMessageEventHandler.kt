package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import net.dv8tion.jda.api.events.GenericEvent

public interface CommandMessageEventHandler {

	public suspend fun handleEvent(event: GenericEvent): EventHandlingResult
}
