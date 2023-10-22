package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.wait

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager.ShutdownManager
import javax.inject.Inject

public class ShutdownManagerAwaiter @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : ShutdownAwaiter {

	override fun awaitShutdownRequest() {
		this.shutdownManager.awaitShutdownRequest()
	}
}
