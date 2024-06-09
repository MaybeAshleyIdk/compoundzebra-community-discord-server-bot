package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.resource

@JvmInline
public value class ResourceTypeName(private val string: String) {

	init {
		require(this.string.isValidResourceTypeName()) {
			"Invalid resource type name string \"${this.string}\""
		}
	}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public fun ofStringOrThrow(nameString: String): ResourceTypeName {
			require(nameString.isValidResourceTypeName()) {
				"Invalid resource type name string \"$nameString\""
			}

			return ResourceTypeName(nameString)
		}
	}
}

private fun String.isValidResourceTypeName(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	if (!(this.all(Char::isValidResourceTypeNameChar))) {
		return false
	}

	if (!(this.first().isValidFirstResourceTypeNameChar())) {
		return false
	}

	if (!(this.last().isValidLastResourceTypeNameChar())) {
		return false
	}

	return true
}

private fun Char.isValidFirstResourceTypeNameChar(): Boolean {
	return (this in 'A'..'Z')
}

private fun Char.isValidResourceTypeNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}

private fun Char.isValidLastResourceTypeNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}
