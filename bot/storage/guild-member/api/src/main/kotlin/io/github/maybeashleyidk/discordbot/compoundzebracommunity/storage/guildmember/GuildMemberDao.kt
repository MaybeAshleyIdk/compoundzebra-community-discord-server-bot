package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.guildmember

public interface GuildMemberDao {

	public suspend fun findMemberByIds(guildId: ULong, userId: ULong): GuildMemberEntity?
}
