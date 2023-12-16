package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler.EventHandler
import net.dv8tion.jda.api.events.GenericEvent

public interface PollEventHandler : EventHandler {

	override suspend fun handleEvent(event: GenericEvent)
}
