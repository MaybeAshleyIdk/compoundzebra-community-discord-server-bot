package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowneventhandler

import net.dv8tion.jda.api.events.GenericEvent

public interface ShutdownEventHandler {

	public enum class Status {
		STILL_RUNNING,
		SHUTTING_DOWN,
	}

	public fun handleEvent(event: GenericEvent): Status
}
