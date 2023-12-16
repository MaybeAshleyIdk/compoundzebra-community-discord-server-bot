package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId

@JvmInline
public value class PollId private constructor(private val snowflakeId: SnowflakeId) {

	public companion object {

		public fun ofSnowflakeId(snowflakeId: SnowflakeId): PollId {
			return PollId(snowflakeId)
		}

		public fun ofULong(uLong: ULong): PollId {
			val snowflakeId: SnowflakeId = SnowflakeId.ofULong(uLong)
			return this.ofSnowflakeId(snowflakeId)
		}
	}

	override fun toString(): String {
		return this.snowflakeId.toString()
	}
}
