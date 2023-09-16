package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging

import java.time.Instant
import javax.annotation.CheckReturnValue
import javax.inject.Inject

public class StderrLogger @Inject constructor() : Logger {

	override fun logError(e: Throwable, msg: String) {
		this.log(LogLevel.ERROR, msg, throwable = e)
	}

	override fun logWarning(msg: String) {
		this.log(LogLevel.WARNING, msg, throwable = null)
	}

	override fun logInfo(msg: String) {
		this.log(LogLevel.INFO, msg, throwable = null)
	}

	override fun logDebug(msg: String) {
		this.log(LogLevel.DEBUG, msg, throwable = null)
	}

	private fun log(level: LogLevel, unformattedMsg: String, throwable: Throwable?) {
		val instant: Instant = Instant.now()

		val formattedMsg: String = this.formatMessage(instant, level, unformattedMsg, throwable)
		this.writeMessage(formattedMsg)
	}

	@CheckReturnValue
	private fun formatMessage(instant: Instant, level: LogLevel, unformattedMsg: String, throwable: Throwable?): String {
		val stackTraceString: String = throwable?.stackTraceToString()
			.orEmpty()

		val sb = StringBuilder(if (throwable == null) 128 else 4096)

		sb.append('[')
		sb.append(instant.toString())
		sb.append("] ")
		sb.append(level.toChar())
		sb.append(": ")
		sb.append(unformattedMsg)

		if (unformattedMsg.isNotEmpty() && stackTraceString.isNotEmpty()) {
			sb.append(": ")
		}

		sb.append(stackTraceString)

		return sb.toString()
	}

	@Synchronized
	private fun writeMessage(message: String) {
		System.err.println(message)
	}
}

@CheckReturnValue
private fun LogLevel.toChar(): Char {
	return when (this) {
		LogLevel.ERROR -> 'E'
		LogLevel.WARNING -> 'W'
		LogLevel.INFO -> 'I'
		LogLevel.DEBUG -> 'D'
	}
}
