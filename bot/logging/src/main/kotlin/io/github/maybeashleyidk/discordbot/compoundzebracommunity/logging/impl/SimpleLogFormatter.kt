package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.impl

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.LogFormatter
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.LogLevel
import java.time.Instant
import javax.annotation.CheckReturnValue

/**
 * Formats log messages in the foramt of `[<timestamp>] <level>: <message>`
 */
internal class SimpleLogFormatter : LogFormatter() {

	private val sharedStringBuilder: StringBuilder = StringBuilder(32)

	@CheckReturnValue
	override fun format(instant: Instant, level: LogLevel, unformattedMsg: String): String {
		instant.epochSecond
		return this.sharedStringBuilder.clear()
			.append('[')
			.append(instant.toString())
			.append("] ")
			.appendLogLevel(level)
			.append(": ")
			.append(unformattedMsg)
			.toString()
	}
}

private fun StringBuilder.appendLogLevel(level: LogLevel): StringBuilder {
	return when (level) {
		LogLevel.DEBUG -> this.append('D')
		LogLevel.INFO -> this.append('I')
		LogLevel.WARNING -> this.append('W')
		LogLevel.ERROR -> this.append('E')
	}
}
