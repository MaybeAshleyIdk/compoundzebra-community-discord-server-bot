package io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler.EventHandler
import net.dv8tion.jda.api.events.GenericEvent

public interface PrivateMessageEventHandler : EventHandler {

	override suspend fun handleEvent(event: GenericEvent)
}
