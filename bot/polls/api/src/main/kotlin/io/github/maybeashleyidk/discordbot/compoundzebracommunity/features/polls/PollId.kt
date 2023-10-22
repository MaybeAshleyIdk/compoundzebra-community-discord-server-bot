package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId

@JvmInline
public value class PollId(private val snowflakeId: SnowflakeId) {

	public companion object {

		public fun ofULong(uLong: ULong): PollId {
			val snowflakeId: SnowflakeId = SnowflakeId.ofULong(uLong)
			return PollId(snowflakeId)
		}
	}

	override fun toString(): String {
		return this.snowflakeId.toString()
	}
}
