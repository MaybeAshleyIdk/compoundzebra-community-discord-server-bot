package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.guildmember

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.guildpoints.GuildPoints
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.Database
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.Duration
import javax.inject.Inject

public class DbGuildMemberDao @Inject constructor(
	private val database: Database,
) : GuildMemberDao {

	override suspend fun findMemberByIds(guildId: ULong, userId: ULong): GuildMemberEntity? {
		return this.database.visitConnectionForReading { connection: Connection ->
			connection.prepareStatement("SELECT * FROM `GuildMember` WHERE `guildId` = ? AND `userId` = ?")
				.use { statement: PreparedStatement ->
					findMemberByIds(statement, guildId, userId)
				}
		}
	}
}

private fun findMemberByIds(statement: PreparedStatement, guildId: ULong, userId: ULong): GuildMemberEntity? {
	statement.setLong(1, guildId.toLong())
	statement.setLong(2, userId.toLong())

	return statement.executeQuery()
		.use { resultSet: ResultSet ->
			resultSet.zeroOrSingleRow(ResultSet::extractGuildMember)
		}
}

private fun <T : Any> ResultSet.zeroOrSingleRow(extractor: (ResultSet) -> T): T? {
	if (!(this.next())) {
		return null
	}

	val value: T = extractor(this)

	check(!(this.next())) {
		"The result set has more than one row"
	}

	return value
}

private fun ResultSet.extractGuildMember(): GuildMemberEntity {
	val guildId: ULong = this.getLong("guildId").toULong()
	val userId: ULong = this.getLong("userId").toULong()
	val points: GuildPoints = this.extractGuildMemberPoints(columnLabel = "points")
	val currentSelfTimeoutDuration: Duration? = this.getString("currentSelfTimeoutDuration")?.let(Duration::parse)

	return GuildMemberEntity(
		guildId,
		userId,
		points,
		currentSelfTimeoutDuration,
	)
}
