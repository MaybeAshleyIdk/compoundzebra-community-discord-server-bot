package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId

@JvmInline
public value class PollId private constructor(private val snowflakeId: SnowflakeId) {

	override fun toString(): String {
		return this.snowflakeId.toString()
	}

	public companion object {

		public fun ofSnowflakeId(snowflakeId: SnowflakeId): PollId {
			return PollId(snowflakeId)
		}

		public fun ofULong(idULong: ULong): PollId {
			val snowflakeId: SnowflakeId = SnowflakeId.ofULong(idULong)
			return this.ofSnowflakeId(snowflakeId)
		}
	}
}
