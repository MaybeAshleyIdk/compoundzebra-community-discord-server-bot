package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.generic

@JvmInline
public value class PermissionName private constructor(private val string: String) {

	init {
		require(this.string.isValidPermissionName()) {
			"Invalid permission name string \"${this.string}\""
		}
	}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public fun ofStringOrThrow(nameString: String): PermissionName {
			require(nameString.isValidPermissionName()) {
				"Invalid permission name string \"$nameString\""
			}

			return PermissionName(nameString)
		}
	}
}

internal fun String.isValidPermissionName(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	if (!(this.all(Char::isValidPermissionNameChar))) {
		return false
	}

	if (!(this.first().isValidFirstPermissionNameChar())) {
		return false
	}

	if (!(this.last().isValidLastPermissionNameChar())) {
		return false
	}

	if ("&&" in this) {
		return false
	}

	return true
}

private fun Char.isValidFirstPermissionNameChar(): Boolean {
	return (this in 'a'..'z')
}

private fun Char.isValidPermissionNameChar(): Boolean {
	return (this in 'a'..'z') || (this == '&') || (this in 'A'..'Z') || (this in '0'..'9')
}

private fun Char.isValidLastPermissionNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}
