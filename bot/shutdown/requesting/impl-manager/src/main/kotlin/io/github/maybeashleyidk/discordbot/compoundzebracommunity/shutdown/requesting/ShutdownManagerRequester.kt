package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager
import javax.inject.Inject

public class ShutdownManagerRequester @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : ShutdownRequester {

	override fun requestShutdown() {
		this.shutdownManager.requestShutdown()
	}
}
