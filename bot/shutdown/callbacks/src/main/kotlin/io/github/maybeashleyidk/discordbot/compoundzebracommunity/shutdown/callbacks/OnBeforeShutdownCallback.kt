package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks

@FunctionalInterface
public fun interface OnBeforeShutdownCallback {

	/**
	 * Gets called when a shutdown is planned in the near future, but before the shutdown has been initiated.
	 *
	 * This callback should be used to prevent new resources from being allocated.
	 * Events may still be pushed to the registered event listeners.
	 *
	 * As such, this callback should *not* be used to clean up any allocated resources.
	 * Use [OnAfterShutdownCallback] for a callback that is suitable for cleanup.
	 */
	public fun onBeforeShutdown()
}
