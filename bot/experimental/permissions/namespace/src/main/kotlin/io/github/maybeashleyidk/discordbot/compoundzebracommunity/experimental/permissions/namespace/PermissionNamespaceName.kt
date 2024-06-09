package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespace

@JvmInline
public value class PermissionNamespaceName private constructor(private val string: String) {

	init {
		require(this.string.isValidPermissionNamespaceName()) {
			"Invalid permission namespace name string \"${this.string}\""
		}
	}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public sealed class ParseResult {

			public data object Valid(name: PermissionNamespaceName)
		}

		public fun parseEntireString(nameString: String) {}

		public fun ofStringOrThrow(nameString: String): PermissionNamespaceName {
			require(nameString.isValidPermissionNamespaceName()) {
				"Invalid permission namespace name string \"$nameString\""
			}

			return PermissionNamespaceName(nameString)
		}
	}
}

internal fun String.isValidPermissionNamespaceName(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	if (!(this.all(Char::isValidPermissionNamespaceNameChar))) {
		return false
	}

	if (!(this.first().isValidFirstPermissionNamespaceNameChar())) {
		return false
	}

	if (!(this.last().isValidLastPermissionNamespaceNameChar())) {
		return false
	}

	return true
}

private fun Char.isValidFirstPermissionNamespaceNameChar(): Boolean {
	return (this in 'a'..'z')
}

private fun Char.isValidPermissionNamespaceNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}

private fun Char.isValidLastPermissionNamespaceNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}
