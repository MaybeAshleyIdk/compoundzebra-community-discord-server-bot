package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks

@FunctionalInterface
public fun interface OnAfterShutdownCallback {

	/**
	 * Gets called after the associated JDA instance has fully shut down.
	 *
	 * This callback should be used to clean up any allocated resources.
	 * No new events (should) be pushed to the registered event listeners.
	 */
	public fun onAfterShutdown()
}
