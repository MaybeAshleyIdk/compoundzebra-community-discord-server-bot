package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownwait

public interface ShutdownAwaiter {

	/**
	 * Blocks the current thread until a shutdown has been requested.
	 * When and how this request is made is implementation-defined.
	 */
	public fun awaitShutdownRequest()
}
