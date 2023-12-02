package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode.DbSchemaVersionCode
import java.sql.Statement

private class DatabaseMigrationException(cause: Exception) : RuntimeException("Database migrations failed", cause)

internal fun runDbMigrations(statement: Statement, beginVersionCode: DbSchemaVersionCode, logger: Logger) {
	require(beginVersionCode < EXPECTED_DB_SCHEMA_VERSION_CODE) {
		"The schema version code ($beginVersionCode) of the database must be less than " +
			"the expected one schema version code ($EXPECTED_DB_SCHEMA_VERSION_CODE)"
	}

	logger.logInfo("Running the database migrations...")

	try {
		// nothing yet
	} catch (e: Throwable) {
		logger.logError(e, "Failed to run the database migrations")

		throw (if (e is Exception) DatabaseMigrationException(e) else e)
	}

	logger.logInfo("Successfully ran the database migrations")
}
