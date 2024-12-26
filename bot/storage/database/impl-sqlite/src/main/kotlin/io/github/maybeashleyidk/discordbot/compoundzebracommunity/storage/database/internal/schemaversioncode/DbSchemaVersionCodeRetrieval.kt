package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.logging.Logger
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.userversion.DbUserVersion
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.userversion.readDbUserVersion
import java.sql.Statement

/**
 * Returns `null` if the database is not initialized yet.
 */
internal fun readDbSchemaVersionCode(statement: Statement): DbSchemaVersionCode? {
	val dbUserVersion: DbUserVersion = readDbUserVersion(statement)

	check(dbUserVersion >= DbUserVersion.ZERO) {
		"The `user_version` of the database ($dbUserVersion) is unexpectedly negative. Is this a foreign database??"
	}

	if (dbUserVersion == DbUserVersion.ZERO) {
		return null
	}

	return DbSchemaVersionCode(dbUserVersion.toInt())
}

/**
 * Returns `null` if the database is not initialized yet.
 */
internal fun readDbSchemaVersionCode(statement: Statement, logger: Logger): DbSchemaVersionCode? {
	val dbSchemaVersionCode: DbSchemaVersionCode? = readDbSchemaVersionCode(statement)

	if (dbSchemaVersionCode == null) {
		val msg: String = "The `user_version` of the database is 0. " +
			"Either the database file doesn't exist yet (the most likely option), " +
			"the database is not initialized yet or " +
			"this is a foreign database. If it is the latter most case, then what the fuck"
		logger.logInfo(msg)
	}

	return dbSchemaVersionCode
}
