package io.github.maybeashleyidk.discordbot.compoundzebracommunity.guildpoints

public sealed class GuildPoints {

	public data class Finite(
		val amount: UInt,
	) : GuildPoints()

	public data object Infinite : GuildPoints()
}
