package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.SqliteDatabase
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.DiModule
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.di.scope.DiScope

public class StorageModule(
	scope: DiScope,
	private val shutdownCallbackRegistry: ShutdownCallbackRegistry,
	private val shutdownRequester: ShutdownRequester,
	private val logger: Logger,
) : DiModule(scope) {

	public val database: Database by this.singleton {
		SqliteDatabase(this.shutdownCallbackRegistry, this.shutdownRequester, this.logger)
	}
}
