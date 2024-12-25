package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnBeforeShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.management.ShutdownManager

public class ShutdownManagerCallbackRegistry(private val shutdownManager: ShutdownManager) : ShutdownCallbackRegistry {

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
