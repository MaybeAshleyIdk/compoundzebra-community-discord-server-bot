package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.guildmember

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.guildpoints.GuildPoints
import java.time.Duration

public data class GuildMemberEntity(
	public val guildId: ULong,
	public val userId: ULong,
	public val points: GuildPoints,
	public val currentSelfTimeoutDuration: Duration?,
)
