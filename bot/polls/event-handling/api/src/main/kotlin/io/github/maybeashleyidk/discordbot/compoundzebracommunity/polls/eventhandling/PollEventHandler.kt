package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.eventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.genericeventhandler.GenericEventHandler
import net.dv8tion.jda.api.events.GenericEvent

public interface PollEventHandler : GenericEventHandler {

	override suspend fun handleEvent(event: GenericEvent)
}
