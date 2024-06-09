package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnBeforeShutdownCallback
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

	public fun requestShutdown()
}
