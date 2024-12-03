package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.SqliteDatabase
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.Provider
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.getValue
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope

public class StorageModule(
	scope: DiScope,
	shutdownCallbackRegistry: Provider<ShutdownCallbackRegistry>,
	shutdownRequester: Provider<ShutdownRequester>,
	logger: Provider<Logger>,
) : DiModule(scope) {

	private val shutdownCallbackRegistry: ShutdownCallbackRegistry by shutdownCallbackRegistry
	private val shutdownRequester: ShutdownRequester by shutdownRequester
	private val logger: Logger by logger

	public val database: Database by this.singleton {
		SqliteDatabase(this.shutdownCallbackRegistry, this.shutdownRequester, this.logger)
	}
}
