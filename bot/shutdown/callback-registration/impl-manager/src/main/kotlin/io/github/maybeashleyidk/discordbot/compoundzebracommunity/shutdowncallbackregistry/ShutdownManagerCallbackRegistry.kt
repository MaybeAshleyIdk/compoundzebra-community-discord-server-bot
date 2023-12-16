package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbackregistry

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnBeforeShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownmanager.ShutdownManager
import javax.inject.Inject

public class ShutdownManagerCallbackRegistry @Inject constructor(
	private val shutdownManager: ShutdownManager,
) : ShutdownCallbackRegistry {

	override fun registerCallback(callback: OnBeforeShutdownCallback) {
		this.shutdownManager.registerShutdownCallback(callback)
	}

	override fun registerCallback(callback: OnAfterShutdownCallback) {
		this.shutdownManager.registerShutdownCallback(callback)
	}

	override fun unregisterCallback(callback: OnAfterShutdownCallback) {
		this.shutdownManager.unregisterShutdownCallback(callback)
	}

	override fun unregisterCallback(callback: OnBeforeShutdownCallback) {
		this.shutdownManager.unregisterShutdownCallback(callback)
	}
}
