package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.guildmember

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.guildpoints.GuildPoints
import java.sql.ResultSet
import java.sql.Types

internal fun ResultSet.extractGuildMemberPoints(columnLabel: String): GuildPoints {
	val columnIndex: Int = this.findColumn(columnLabel)
	val columnType: Int = this.metaData.getColumnType(columnIndex)
	return when (columnType) {
		Types.TINYINT,
		Types.SMALLINT,
		Types.INTEGER,
		Types.BIGINT,
		-> {
			val points: Int = this.getInt(columnIndex)
			check(points >= 0) {
				"Guild member points must not be negative"
			}

			GuildPoints.Finite(points.toUInt())
		}

		Types.FLOAT,
		Types.DOUBLE,
		Types.REAL,
		Types.DECIMAL,
		-> {
			val points: Double = this.getDouble(columnIndex)

			check(points == Double.POSITIVE_INFINITY) {
				"If the guild member points are not an integer, they must be positive infinity"
			}

			GuildPoints.Infinite
		}

		else -> {
			val columnTypeName: String = this.metaData.getColumnTypeName(columnIndex)
			error("Invalid type `$columnTypeName` for column `points`")
		}
	}
}
