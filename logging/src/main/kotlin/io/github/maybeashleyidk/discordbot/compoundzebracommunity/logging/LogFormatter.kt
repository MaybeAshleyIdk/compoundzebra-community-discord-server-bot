package io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging

import java.time.Instant
import javax.annotation.CheckReturnValue

internal abstract class LogFormatter {

	@CheckReturnValue
	abstract fun format(instant: Instant, level: LogLevel, unformattedMsg: String): String
}
