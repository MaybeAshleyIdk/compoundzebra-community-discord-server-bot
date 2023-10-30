package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownwait

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager.ShutdownManager
import javax.inject.Inject

public class ShutdownManagerAwaiter @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : ShutdownAwaiter {

	override fun awaitShutdownRequest() {
		this.shutdownManager.awaitShutdownRequest()
	}
}
