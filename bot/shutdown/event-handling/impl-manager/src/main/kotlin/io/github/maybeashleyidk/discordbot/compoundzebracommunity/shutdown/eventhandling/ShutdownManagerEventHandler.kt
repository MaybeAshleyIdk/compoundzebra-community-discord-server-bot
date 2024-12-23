package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.eventhandling

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager
import net.dv8tion.jda.api.events.GenericEvent

public class ShutdownManagerEventHandler(private val shutdownManager: ShutdownManager) : ShutdownEventHandler {

	override suspend fun handleEvent(event: GenericEvent): ShutdownEventHandler.Status {
		return when (this.shutdownManager.handleShutdownEvent(event)) {
			ShutdownManager.EventHandlingResultStatus.STILL_RUNNING -> ShutdownEventHandler.Status.STILL_RUNNING
			ShutdownManager.EventHandlingResultStatus.SHUTTING_DOWN -> ShutdownEventHandler.Status.SHUTTING_DOWN
		}
	}
}
