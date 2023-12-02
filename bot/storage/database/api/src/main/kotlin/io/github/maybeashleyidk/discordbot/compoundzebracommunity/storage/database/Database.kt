package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database

import java.sql.Connection

public interface Database {

	public suspend fun <R> visitConnectionForReading(block: suspend (Connection) -> R): R

	public suspend fun <R> visitConnectionForWriting(block: suspend (Connection) -> R): R
}
