package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.schemaversioncode

@JvmInline
internal value class DbSchemaVersionCode private constructor(
	private val integer: Int,
) : Comparable<DbSchemaVersionCode> {

	companion object {

		fun ofInt(integer: Int): DbSchemaVersionCode {
			require(integer > 0) {
				"Database schema version code must be positive"
			}

			return DbSchemaVersionCode(integer)
		}
	}

	init {
		require(this.integer > 0) {
			"Database schema version code must be positive"
		}
	}

	override fun compareTo(other: DbSchemaVersionCode): Int {
		return this.integer.compareTo(other.integer)
	}

	override fun toString(): String {
		return this.integer.toString()
	}
}
