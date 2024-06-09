package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database

import dagger.Binds
import dagger.Module

@Module(includes = [StorageDatabaseModule.Bindings::class])
public object StorageDatabaseModule {

	@Module
	internal interface Bindings {

		@Binds
		fun bindDatabase(sqliteDatabase: SqliteDatabase): Database
	}
}
