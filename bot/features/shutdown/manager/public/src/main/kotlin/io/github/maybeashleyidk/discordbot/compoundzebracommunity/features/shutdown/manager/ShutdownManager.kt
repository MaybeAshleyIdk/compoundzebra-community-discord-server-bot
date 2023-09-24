package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.manager

public interface ShutdownManager {

	public fun requestShutdown()

	/**
	 * Blocks the current thread until a shutdown has been requested by calling the function [requestShutdown].
	 */
	public fun awaitShutdownRequest()
}
