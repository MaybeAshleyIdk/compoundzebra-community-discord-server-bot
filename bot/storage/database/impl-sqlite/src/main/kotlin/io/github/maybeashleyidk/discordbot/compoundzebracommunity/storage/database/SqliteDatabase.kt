package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbackregistry.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdowncallbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdownrequest.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.EXPECTED_DB_SCHEMA_VERSION_CODE
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.NoRetryLazy
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.initializeDatabase
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.runDbMigrations
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.DbSchemaVersionCode
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.readDbSchemaVersionCode
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines.ReadWriteMutex
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines.withReadLock
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utilscoroutines.withWriteLock
import kotlinx.coroutines.runBlocking
import okio.Path
import okio.Path.Companion.toPath
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import javax.inject.Inject
import javax.inject.Singleton

private val DB_FILE_PATH: Path = "storage.sqlite.db".toPath()

@Singleton
public class SqliteDatabase @Inject constructor(
	shutdownCallbackRegistry: ShutdownCallbackRegistry,
	shutdownRequester: ShutdownRequester,
	private val logger: Logger,
) : Database {

	private val lazyConnectionReadWriteMutex: ReadWriteMutex = ReadWriteMutex()
	private var lazyConnection: NoRetryLazy<Connection>? = NoRetryLazy {
		try {
			openDbConnectionAndInitializeDbOrRunMigrations(this.logger)
		} catch (e: Throwable) {
			this.logger.logError(e, "An error occurred setting up the database. Requesting shutdown...")
			shutdownRequester.requestShutdown()
			throw e
		}
	}

	init {
		val callback = OnAfterShutdownCallback {
			runBlocking {
				this@SqliteDatabase.closeDbConnection()
			}
		}

		shutdownCallbackRegistry.registerCallback(callback)
	}

	override suspend fun <R> visitConnectionForReading(block: suspend (Connection) -> R): R {
		return this.lazyConnectionReadWriteMutex.withReadLock {
			block(this.getConnectionWithoutLock())
		}
	}

	override suspend fun <R> visitConnectionForWriting(block: suspend (Connection) -> R): R {
		return this.lazyConnectionReadWriteMutex.withWriteLock {
			block(this.getConnectionWithoutLock())
		}
	}

	private fun getConnectionWithoutLock(): Connection {
		val localLazyConnection: NoRetryLazy<Connection>? = this.lazyConnection
		if (localLazyConnection == null) {
			val msg = "Failed to get the database connection; it is already closed"
			this.logger.logWarning(msg)
			error(msg)
		}

		return localLazyConnection.getValue()
	}

	private suspend fun closeDbConnection() {
		this.logger.logInfo("Closing the database connection...")

		val localLazyConnection: NoRetryLazy<Connection>? =
			this.lazyConnectionReadWriteMutex.withWriteLock {
				val localLazyConnection: NoRetryLazy<Connection>? = this.lazyConnection
				this.lazyConnection = null
				localLazyConnection
			}

		if (localLazyConnection == null) {
			this.logger.logWarning("Could not close the database connection; the connection is already closed")
			return
		}

		if (!(localLazyConnection.isInitializedSuccessfully())) {
			this.logger.logWarning("Could not close the database connection; the connection was never opened")
			return
		}

		val connection: Connection = localLazyConnection.getValue()

		try {
			connection.close()
		} catch (e: Throwable) {
			this.logger.logError(e, "Failed to close the database connection")

			if (e !is Exception) {
				throw e
			}

			return
		}

		this.logger.logInfo("Successfully closed the database connection")
	}
}

private fun openDbConnectionAndInitializeDbOrRunMigrations(logger: Logger): Connection {
	logger.logInfo("Opening the database connection...")

	val connection: Connection =
		try {
			DriverManager.getConnection("jdbc:sqlite:$DB_FILE_PATH")
		} catch (e: Throwable) {
			logger.logError(e, "Failed to open the database connection")
			throw e
		}

	try {
		logger.logInfo("Successfully opened the database connection")

		connection.createStatement()
			.use { statement: Statement ->
				initializeDbOrRunMigrations(statement, logger)
			}

		return connection
	} catch (e: Throwable) {
		try {
			connection.close()
		} catch (closeException: Throwable) {
			e.addSuppressed(closeException)
		}

		throw e
	}
}

private fun initializeDbOrRunMigrations(statement: Statement, logger: Logger) {
	val dbSchemaVersionCode: DbSchemaVersionCode? = readDbSchemaVersionCode(statement, logger)

	when {
		(dbSchemaVersionCode == null) -> {
			initializeDatabase(statement, logger)
		}

		(dbSchemaVersionCode > EXPECTED_DB_SCHEMA_VERSION_CODE) -> {
			val msg: String = "The schema version code ($dbSchemaVersionCode) of the database is greater than " +
				"the expected one schema version code ($EXPECTED_DB_SCHEMA_VERSION_CODE)"
			error(msg)
		}

		(dbSchemaVersionCode < EXPECTED_DB_SCHEMA_VERSION_CODE) -> {
			runDbMigrations(statement, beginVersionCode = dbSchemaVersionCode, logger)
		}
	}
}
