package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging

import java.time.Instant

public class Logger internal constructor(
	private val logFormatter: LogFormatter,
	private val logWriter: LogWriter,
) {

	public fun logError(msg: String) {
		this.log(LogLevel.ERROR, msg)
	}

	public fun logWarning(msg: String) {
		this.log(LogLevel.WARNING, msg)
	}

	public fun logInfo(msg: String) {
		this.log(LogLevel.INFO, msg)
	}

	public fun logDebug(msg: String) {
		this.log(LogLevel.DEBUG, msg)
	}

	@Synchronized
	private fun log(level: LogLevel, unformattedMsg: String) {
		val instant: Instant = Instant.now()
		val formattedMsg: String = this.logFormatter.format(instant, level, unformattedMsg)
		this.logWriter.write(formattedMsg)
	}
}
