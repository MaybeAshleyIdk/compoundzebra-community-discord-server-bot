package io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake

import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import javax.annotation.CheckReturnValue

public class SnowflakeIdGenerator {

	private companion object {

		val EPOCH_BEGIN: Long = LocalDate.of(2023, Month.AUGUST, 1)
			.atStartOfDay()
			.atOffset(ZoneOffset.UTC)
			.toInstant()
			.toEpochMilli()
	}

	private var currentSequenceNr: UShort = 0u

	@CheckReturnValue
	public fun generateNext(): SnowflakeId {
		val now: Long = System.currentTimeMillis()

		@Suppress("RemoveRedundantQualifierName")
		val snowflakeId: SnowflakeId = SnowflakeId
			.fromParts(
				(now - SnowflakeIdGenerator.EPOCH_BEGIN),
				workerId = 0u, // maybe at one point we'll add more workers or something
				this.currentSequenceNr,
			)

		++(this.currentSequenceNr)

		return snowflakeId
	}
}
