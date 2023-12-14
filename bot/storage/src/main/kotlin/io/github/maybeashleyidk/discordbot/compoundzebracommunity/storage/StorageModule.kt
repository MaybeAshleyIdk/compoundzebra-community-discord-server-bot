package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.StorageDatabaseModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.guildmember.StorageGuildMemberModule

@Module(
	includes = [
		StorageDatabaseModule::class,
		StorageGuildMemberModule::class,
	],
)
public object StorageModule
