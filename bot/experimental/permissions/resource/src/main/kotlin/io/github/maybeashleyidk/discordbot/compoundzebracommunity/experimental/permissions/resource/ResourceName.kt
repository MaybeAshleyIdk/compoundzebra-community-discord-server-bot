package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.resource

@JvmInline
public value class ResourceName private constructor(private val string: String) {

	init {
		require(this.string.isValidResourceName()) {
			"Invalid resource name string \"${this.string}\""
		}
	}

	override fun toString(): String {
		return this.string
	}

	public companion object {

		public fun ofStringOrThrow(nameString: String): ResourceName {
			require(nameString.isValidResourceName()) {
				"Invalid resource name string \"$nameString\""
			}

			return ResourceName(nameString)
		}
	}
}

private fun String.isValidResourceName(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	if (!(this.all(Char::isValidResourceNameChar))) {
		return false
	}

	if (!(this.first().isValidFirstResourceNameChar())) {
		return false
	}

	if (!(this.last().isValidLastResourceNameChar())) {
		return false
	}

	return true
}

private fun Char.isValidFirstResourceNameChar(): Boolean {
	return (this in 'a'..'z')
}

private fun Char.isValidResourceNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}

private fun Char.isValidLastResourceNameChar(): Boolean {
	return (this in 'a'..'z') || (this in 'A'..'Z') || (this in '0'..'9')
}
