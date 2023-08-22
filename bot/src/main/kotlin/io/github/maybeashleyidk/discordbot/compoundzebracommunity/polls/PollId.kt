package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId
import javax.annotation.CheckReturnValue

@JvmInline
internal value class PollId(private val snowflakeId: SnowflakeId) {

	companion object {

		@CheckReturnValue
		fun ofULong(uLong: ULong): PollId {
			val snowflakeId: SnowflakeId = SnowflakeId.ofULong(uLong)
			return PollId(snowflakeId)
		}
	}

	@CheckReturnValue
	override fun toString(): String {
		return this.snowflakeId.toString()
	}
}
