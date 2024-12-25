package io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.management

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.polls.id.PollId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflakegenerator.SnowflakeIdGenerator

internal class PollIdGenerator {

	private val snowflakeIdGenerator: SnowflakeIdGenerator = SnowflakeIdGenerator()

	internal fun generate(): PollId {
		val snowflakeId: SnowflakeId = this.snowflakeIdGenerator.generateNext()
		return PollId.ofSnowflakeId(snowflakeId)
	}
}
