package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager.ShutdownManager
import javax.inject.Inject

public class ShutdownManagerRequester @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : ShutdownRequester {

	override fun requestShutdown() {
		this.shutdownManager.requestShutdown()
	}
}
