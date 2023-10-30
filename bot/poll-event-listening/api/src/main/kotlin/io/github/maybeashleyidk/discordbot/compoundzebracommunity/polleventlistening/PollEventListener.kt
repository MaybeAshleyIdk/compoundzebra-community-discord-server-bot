package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polleventlistening

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener

public interface PollEventListener : EventListener {

	override fun onEvent(event: GenericEvent)
}
