package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.shutdown.request

public interface ShutdownRequester {

	/**
	 * Requests the shutdown of the bot.
	 * When and how the bot is shutdown is implementation-defined.
	 */
	public fun requestShutdown()
}
