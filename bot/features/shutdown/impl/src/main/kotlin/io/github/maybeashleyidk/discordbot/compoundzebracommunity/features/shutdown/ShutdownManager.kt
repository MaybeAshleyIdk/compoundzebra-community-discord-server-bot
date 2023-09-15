package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown

public interface ShutdownManager : ShutdownRequester {

	public override fun requestShutdown()

	/**
	 * Blocks the current thread until a shutdown has been requested by calling the function [requestShutdown].
	 */
	public fun waitForShutdownRequest()
}
