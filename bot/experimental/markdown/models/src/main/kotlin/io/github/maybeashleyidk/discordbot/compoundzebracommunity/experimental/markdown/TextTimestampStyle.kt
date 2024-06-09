package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalAccessor
import java.util.Locale

public enum class TimestampStyle(public val ch: Char) {
	TIME_SHORT('t'),
	TIME_LONG('T'),
	DATE_SHORT('d'),
	DATE_LONG('D'),
	DATETIME_SHORT('f'),
	DATETIME_LONG('F'),
	TIME_RELATIVE('R'),
	;

	public companion object {

		public val DEFAULT: TimestampStyle = DATETIME_SHORT
	}
}

internal fun Instant.format(timestampStyle: TimestampStyle, locale: Locale): String {
	val formatter: DateTimeFormatter = timestampStyle.getFormatter()
		.localizedBy(locale)

	val temporal: TemporalAccessor =
		when (timestampStyle) {
			TimestampStyle.TIME_SHORT,
			TimestampStyle.TIME_LONG,
			-> this.atZone(ZoneOffset.UTC).toLocalTime()

			TimestampStyle.DATE_SHORT,
			TimestampStyle.DATE_LONG,
			-> this.atZone(ZoneOffset.UTC).toLocalDate()

			TimestampStyle.DATETIME_SHORT,
			TimestampStyle.DATETIME_LONG,
			-> this.atZone(ZoneOffset.UTC).toLocalDateTime()

			TimestampStyle.TIME_RELATIVE -> this
		}

	return formatter.format(temporal)
}

private fun TimestampStyle.getFormatter(): DateTimeFormatter {
	val (dateStyle: FormatStyle, timeStyle: FormatStyle) =
		when (this) {
			TimestampStyle.TIME_SHORT -> (FormatStyle.SHORT to FormatStyle.SHORT)
			TimestampStyle.TIME_LONG -> (FormatStyle.SHORT to FormatStyle.LONG)
			TimestampStyle.DATE_SHORT -> (FormatStyle.SHORT to FormatStyle.SHORT)
			TimestampStyle.DATE_LONG -> (FormatStyle.MEDIUM to FormatStyle.SHORT)
			TimestampStyle.DATETIME_SHORT -> (FormatStyle.MEDIUM to FormatStyle.SHORT)
			TimestampStyle.DATETIME_LONG -> (FormatStyle.LONG to FormatStyle.MEDIUM)
			TimestampStyle.TIME_RELATIVE -> {
				return TimestampStyle.DATETIME_SHORT.getFormatter()
			}
		}

	return DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
}
