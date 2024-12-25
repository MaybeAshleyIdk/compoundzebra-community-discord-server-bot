package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager

public class ShutdownManagerRequester(private val shutdownManager: ShutdownManager) : ShutdownRequester {

	override fun requestShutdown() {
		this.shutdownManager.requestShutdown()
	}
}
