package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage

import dagger.Module
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.StorageDatabaseModule

@Module(
	includes = [
		StorageDatabaseModule::class,
	],
)
public object StorageModule
