package io.github.maybeashleyidk.discordbot.compoundzebracommunity.messageeventhandlermediation

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener

public interface MessageEventHandlerMediator : EventListener {

	override fun onEvent(event: GenericEvent)
}
