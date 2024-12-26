package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.userversion

import java.sql.ResultSet
import java.sql.Statement

// <https://sqlite.org/pragma.html#pragma_user_version>
// > The user-version is an integer that is available to applications to use however they want.
// > SQLite makes no use of the user-version itself.

private const val SQLITE_USER_VERSION_RETRIEVAL_STATEMENT: String = "pragma user_version;"

internal fun readDbUserVersion(statement: Statement): DbUserVersion {
	val userVersionInt: Int = statement.executeQuery(SQLITE_USER_VERSION_RETRIEVAL_STATEMENT)
		.use(::extractDbUserVersionIntFromResultSet)

	return DbUserVersion(userVersionInt)
}

private fun extractDbUserVersionIntFromResultSet(resultSet: ResultSet): Int {
	check(resultSet.next()) {
		"Expected one row from the result of `$SQLITE_USER_VERSION_RETRIEVAL_STATEMENT`, but got none"
	}

	val userVersionInt: Int = resultSet.getInt("user_version")

	check(!(resultSet.next())) {
		"Expected only one row from the result of `$SQLITE_USER_VERSION_RETRIEVAL_STATEMENT`, but got multiple"
	}

	return userVersionInt
}
