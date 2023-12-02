package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.tablequery

import java.sql.Blob
import java.sql.ResultSet

internal class ResultSetRow(private val resultSet: ResultSet) : RowResult {

	override fun getLong(columnName: String): Long {
		return this.resultSet.getLong(columnName)
	}

	override fun getDouble(columnName: String): Double {
		return this.resultSet.getDouble(columnName)
	}

	override fun getString(columnName: String): String {
		return this.resultSet.getString(columnName)
	}

	override fun getBlob(columnName: String): Blob {
		return this.resultSet.getBlob(columnName)
	}
}
