package io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting

public interface ShutdownRequester {

	/**
	 * Requests the shutdown of the bot.
	 * When and how the bot is shutdown is implementation-defined.
	 */
	public suspend fun requestShutdown()
}
