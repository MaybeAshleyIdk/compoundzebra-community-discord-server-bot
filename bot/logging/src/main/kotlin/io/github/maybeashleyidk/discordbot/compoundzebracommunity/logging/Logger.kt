package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging

import java.time.Instant

public class Logger internal constructor(
	private val logFormatter: LogFormatter,
	private val logWriter: LogWriter,
) {

	public fun logError(e: Throwable, msg: String) {
		this.log(LogLevel.ERROR, msg, throwable = e)
	}

	public fun logWarning(msg: String) {
		this.log(LogLevel.WARNING, msg, throwable = null)
	}

	public fun logInfo(msg: String) {
		this.log(LogLevel.INFO, msg, throwable = null)
	}

	public fun logDebug(msg: String) {
		this.log(LogLevel.DEBUG, msg, throwable = null)
	}

	@Synchronized
	private fun log(level: LogLevel, unformattedMsg: String, throwable: Throwable?) {
		val instant: Instant = Instant.now()

		val sb = StringBuilder(unformattedMsg)

		val stackTrace: String = throwable?.stackTraceToString()
			.orEmpty()

		if (sb.isNotEmpty() && stackTrace.isNotEmpty()) {
			sb.append(": ")
			sb.append(stackTrace)
		}

		val formattedMsg: String = this.logFormatter.format(instant, level, sb.toString())
		this.logWriter.write(formattedMsg)
	}
}
