package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.guildmember

import dagger.Binds
import dagger.Module

@Module(includes = [StorageGuildMemberModule.Bindings::class])
public object StorageGuildMemberModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindGuildMemberDao(dbGuildMemberDao: DbGuildMemberDao): GuildMemberDao
	}
}
