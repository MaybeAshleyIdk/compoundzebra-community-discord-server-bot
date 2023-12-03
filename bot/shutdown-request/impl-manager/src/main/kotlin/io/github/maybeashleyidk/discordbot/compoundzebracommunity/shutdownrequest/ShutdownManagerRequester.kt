package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownrequest

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager.ShutdownManager
import javax.inject.Inject

public class ShutdownManagerRequester @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : ShutdownRequester {

	override fun requestShutdown() {
		this.shutdownManager.requestShutdown()
	}
}
