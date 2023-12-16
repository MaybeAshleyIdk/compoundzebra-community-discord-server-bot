package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.genericeventhandler.GenericEventHandler
import net.dv8tion.jda.api.events.GenericEvent

public interface MessageEventHandlerMediator : GenericEventHandler {

	override suspend fun handleEvent(event: GenericEvent)
}
