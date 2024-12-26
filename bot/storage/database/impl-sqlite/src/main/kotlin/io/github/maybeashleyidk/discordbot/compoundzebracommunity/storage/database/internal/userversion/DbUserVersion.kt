package io.github.maybeashleyidk.discordbot.compoundzebracommunity.storage.database.internal.userversion

@JvmInline
internal value class DbUserVersion(private val versionInt: Int) : Comparable<DbUserVersion> {

	fun toInt(): Int {
		return this.versionInt
	}

	override fun compareTo(other: DbUserVersion): Int {
		return this.versionInt.compareTo(other.versionInt)
	}

	override fun toString(): String {
		return this.versionInt.toString()
	}

	companion object {

		val ZERO: DbUserVersion = DbUserVersion(0)
	}
}
