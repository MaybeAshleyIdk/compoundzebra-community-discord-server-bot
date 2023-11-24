package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.eventhandler.EventHandler
import net.dv8tion.jda.api.events.GenericEvent

public interface MessageEventHandlerMediator : EventHandler {

	override fun handleEvent(event: GenericEvent)
}
