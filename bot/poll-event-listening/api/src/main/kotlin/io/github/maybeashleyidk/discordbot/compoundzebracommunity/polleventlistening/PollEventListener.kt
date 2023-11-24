package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler.EventHandler
import net.dv8tion.jda.api.events.GenericEvent

public interface PollEventListener : EventHandler {

	override fun handleEvent(event: GenericEvent)
}
