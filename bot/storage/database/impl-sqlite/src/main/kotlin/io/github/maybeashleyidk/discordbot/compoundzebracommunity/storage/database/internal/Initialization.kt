package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.DbSchemaVersionCode
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.readDbSchemaVersionCode
import okio.BufferedSource
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import java.sql.Statement

private class DatabaseInitializationException(cause: Exception) : Exception("Database initialization failed", cause)

private val DATABASE_INITIALIZATION_SQL_FILE_PATH: Path =
	"/io/github/maybeashleyidk/discordbot/compoundzebracommunity/storage/database/init.sqlite.sql".toPath()

internal fun initializeDatabase(statement: Statement, logger: Logger) {
	logger.logInfo("Initializing the database...")

	try {
		val dbInitSql: String = FileSystem.RESOURCES
			.read(
				file = DATABASE_INITIALIZATION_SQL_FILE_PATH,
				readerAction = BufferedSource::readUtf8,
			)

		statement.executeUpdate(dbInitSql)

		verifyDbSchemaVersionCode(statement)
	} catch (e: Throwable) {
		logger.logError(e, "Failed to initialized the database")

		if (e !is Exception) {
			throw e
		}
		throw DatabaseInitializationException(e)
	}

	logger.logInfo("Successfully initialized the database")
}

private fun verifyDbSchemaVersionCode(statement: Statement) {
	val dbSchemaVersionCode: DbSchemaVersionCode? = readDbSchemaVersionCode(statement)

	checkNotNull(dbSchemaVersionCode) {
		"The database doesn't have a valid schema version code after initialization"
	}

	check(dbSchemaVersionCode == EXPECTED_DB_SCHEMA_VERSION_CODE) {
		"The schema version code of the database after initialization ($dbSchemaVersionCode) and " +
			"the expected schema version code ($EXPECTED_DB_SCHEMA_VERSION_CODE) differ. " +
			"Either these weren't changed during development or an error occurred during initialization"
	}
}
