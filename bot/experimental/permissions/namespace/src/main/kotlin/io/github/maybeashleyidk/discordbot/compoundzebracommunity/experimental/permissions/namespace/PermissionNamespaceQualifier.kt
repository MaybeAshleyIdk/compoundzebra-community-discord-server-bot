package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespace

@JvmInline
public value class PermissionNamespaceQualifier private constructor(private val string: String) {

	public constructor(names: Iterable<PermissionNamespaceName>) : this(names.joinToString(separator = "."))

	init {
		require(this.string.isValidPermissionNamespaceQualifier()) {
			"Invalid permission namespace qualifier string \"${this.string}\""
		}
	}

	public val names: Sequence<PermissionNamespaceName>
		get() {
			return this.string.splitToSequence('.')
				.map(PermissionNamespaceName::ofStringOrThrow)
		}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public fun ofStringOrThrow(nameString: String): PermissionNamespaceQualifier {
			require(nameString.isValidPermissionNamespaceQualifier()) {
				"Invalid permission namespace qualifier string \"$nameString\""
			}

			return PermissionNamespaceQualifier(nameString)
		}
	}
}

private fun String.isValidPermissionNamespaceQualifier(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	return this.splitToSequence('.')
		.all(String::isValidPermissionNamespaceName)
}
