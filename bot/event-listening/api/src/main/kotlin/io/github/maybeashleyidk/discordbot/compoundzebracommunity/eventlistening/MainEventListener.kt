package io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventlistening

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener

public interface MainEventListener : EventListener {

	override fun onEvent(event: GenericEvent)
}
