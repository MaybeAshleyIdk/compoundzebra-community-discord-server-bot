package io.github.maybeashleyidk.discordbot.compoundzebracommunity.features.polls

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeId
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake.SnowflakeIdGenerator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PollIdGenerator @Inject constructor() {

	private val snowflakeIdGenerator: SnowflakeIdGenerator = SnowflakeIdGenerator()

	fun generate(): PollId {
		val snowflakeId: SnowflakeId = this.snowflakeIdGenerator.generateNext()
		return PollId(snowflakeId)
	}
}
