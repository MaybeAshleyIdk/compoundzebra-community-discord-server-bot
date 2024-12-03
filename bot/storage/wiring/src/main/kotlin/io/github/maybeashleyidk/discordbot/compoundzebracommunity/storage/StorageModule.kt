package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage

import dagger.Module
import dagger.Provides
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.SqliteDatabase
import javax.inject.Singleton

@Module
public object StorageModule {

	@Provides
	@Singleton
	internal fun provideDatabase(
		shutdownCallbackRegistry: ShutdownCallbackRegistry,
		shutdownRequester: ShutdownRequester,
		logger: Logger,
	): Database {
		return SqliteDatabase(shutdownCallbackRegistry, shutdownRequester, logger)
	}
}
