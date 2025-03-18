package io.github.maybeashleyidk.discordbot.compoundzebracommunity.privatemessageeventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.genericeventhandler.GenericEventHandler
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.eventhandlingresult.EventHandlingResult
import net.dv8tion.jda.api.events.GenericEvent

public interface PrivateMessageEventHandler : GenericEventHandler {

	override suspend fun handleEvent(event: GenericEvent): EventHandlingResult
}
