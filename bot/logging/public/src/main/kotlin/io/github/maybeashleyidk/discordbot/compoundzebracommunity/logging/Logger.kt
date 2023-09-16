package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging

public interface Logger {

	public fun logError(e: Throwable, msg: String)

	public fun logWarning(msg: String)

	public fun logInfo(msg: String)

	public fun logDebug(msg: String)
}
