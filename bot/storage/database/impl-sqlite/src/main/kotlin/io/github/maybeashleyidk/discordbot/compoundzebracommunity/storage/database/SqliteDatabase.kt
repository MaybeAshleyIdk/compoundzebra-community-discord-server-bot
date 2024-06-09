package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbackregistraton.ShutdownCallbackRegistry
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.callbacks.OnAfterShutdownCallback
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.shutdown.requesting.ShutdownRequester
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.EXPECTED_DB_SCHEMA_VERSION_CODE
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.initializeDatabase
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.runDbMigrations
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.DbSchemaVersionCode
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.readDbSchemaVersionCode
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.ReadWriteMutex
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.withReadLock
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesreadwritemutex.withWriteLock
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

	private val connectionMutex: ReadWriteMutex = ReadWriteMutex()
	private var lazyConnectionResult: Lazy<Result<Connection>>? =
		lazy {
			val result: Result<Connection> = kotlin
				.runCatching {
					openDbConnectionAndInitializeDbOrRunMigrations(this.logger)
				}

			val e: Throwable? = result.exceptionOrNull()
			if (e != null) {
				this.logger.logError(e, "An error occurred setting up the database. Requesting shutdown...")
				shutdownRequester.requestShutdown()
			}

			result
		}

	init {
		val callback =
			OnAfterShutdownCallback {
				runBlocking {
					this@SqliteDatabase.closeConnection()
				}
			}

		shutdownCallbackRegistry.registerCallback(callback)
	}

	override suspend fun <R> visitConnectionForReading(block: suspend (Connection) -> R): R {
		return this.connectionMutex
			.withReadLock {
				block(this.getConnectionWithoutLock())
			}
	}

	override suspend fun <R> visitConnectionForWriting(block: suspend (Connection) -> R): R {
		return this.connectionMutex
			.withWriteLock {
				block(this.getConnectionWithoutLock())
			}
	}

	private fun getConnectionWithoutLock(): Connection {
		val lazyConnectionResult: Lazy<Result<Connection>>? = this.lazyConnectionResult

		if (lazyConnectionResult == null) {
			val msg = "Failed to get the database connection; it is already closed"
			this.logger.logWarning(msg)
			error(msg)
		}

		return lazyConnectionResult.value.getOrThrow()
	}

	private suspend fun closeConnection() {
		this.logger.logInfo("Closing the database connection...")

		val lazyConnectionResult: Lazy<Result<Connection>>? =
			this.connectionMutex.withWriteLock {
				val lazyConnectionResult: Lazy<Result<Connection>>? = this.lazyConnectionResult
				this.lazyConnectionResult = null
				lazyConnectionResult
			}

		if (lazyConnectionResult == null) {
			this.logger.logWarning("Could not close the database connection; the connection is already closed")
			return
		}

		val connection: Connection? = lazyConnectionResult.value.getOrNull()

		if (connection == null) {
			val msg = "Could not close the database connection; the connection was never opened due to an error"
			this.logger.logWarning(msg)
			return
		}

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
