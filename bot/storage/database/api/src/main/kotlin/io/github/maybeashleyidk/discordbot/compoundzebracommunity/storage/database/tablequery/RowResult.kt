package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.tablequery

import java.sql.Blob

public interface RowResult {

	public fun getLong(columnName: String): Long

	public fun getDouble(columnName: String): Double

	public fun getString(columnName: String): String

	public fun getBlob(columnName: String): Blob
}
