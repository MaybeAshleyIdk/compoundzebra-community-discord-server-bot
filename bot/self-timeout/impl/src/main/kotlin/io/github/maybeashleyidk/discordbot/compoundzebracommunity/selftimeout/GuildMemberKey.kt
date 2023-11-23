package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import net.dv8tion.jda.api.entities.Member

private typealias GuildSnowflakeId = Long

private typealias UserSnowflakeId = Long

internal data class GuildMemberKey(
	private val guildSnowflakeId: GuildSnowflakeId,
	private val userSnowflakeId: UserSnowflakeId,
) {

	companion object {

		fun ofMember(member: Member): GuildMemberKey {
			return GuildMemberKey(
				guildSnowflakeId = member.guild.idLong,
				userSnowflakeId = member.user.idLong,
			)
		}
	}
}
