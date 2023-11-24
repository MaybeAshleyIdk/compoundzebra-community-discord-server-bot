package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnBeforeShutdownCallback
import net.dv8tion.jda.api.events.GenericEvent

public interface ShutdownManager {

	public enum class EventHandlingResultStatus {
		STILL_RUNNING,
		SHUTTING_DOWN,
	}

	public fun registerShutdownCallback(callback: OnBeforeShutdownCallback)

	public fun registerShutdownCallback(callback: OnAfterShutdownCallback)

	public fun unregisterShutdownCallback(callback: OnAfterShutdownCallback)

	public fun unregisterShutdownCallback(callback: OnBeforeShutdownCallback)

	public suspend fun handleShutdownEvent(event: GenericEvent): EventHandlingResultStatus

	public suspend fun requestShutdown()
}
