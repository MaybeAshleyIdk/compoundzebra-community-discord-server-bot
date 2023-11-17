package io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener

public interface PrivateMessageEventHandler : EventListener {

	override fun onEvent(event: GenericEvent)
}
