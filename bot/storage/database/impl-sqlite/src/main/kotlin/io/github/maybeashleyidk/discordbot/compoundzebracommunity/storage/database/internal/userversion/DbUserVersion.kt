package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.userversion

@JvmInline
internal value class DbUserVersion private constructor(private val integer: Int) : Comparable<DbUserVersion> {

	companion object {

		val ZERO: DbUserVersion = this.ofInt(0)

		fun ofInt(integer: Int): DbUserVersion {
			return DbUserVersion(integer)
		}
	}

	override fun compareTo(other: DbUserVersion): Int {
		return this.integer.compareTo(other.integer)
	}

	fun toInt(): Int {
		return this.integer
	}

	override fun toString(): String {
		return this.integer.toString()
	}
}
