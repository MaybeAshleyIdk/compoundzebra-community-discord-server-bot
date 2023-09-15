package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId
import javax.annotation.CheckReturnValue

@JvmInline
public value class PollId(private val snowflakeId: SnowflakeId) {

	public companion object {

		@CheckReturnValue
		public fun ofULong(uLong: ULong): PollId {
			val snowflakeId: SnowflakeId = SnowflakeId.ofULong(uLong)
			return PollId(snowflakeId)
		}
	}

	@CheckReturnValue
	override fun toString(): String {
		return this.snowflakeId.toString()
	}
}
