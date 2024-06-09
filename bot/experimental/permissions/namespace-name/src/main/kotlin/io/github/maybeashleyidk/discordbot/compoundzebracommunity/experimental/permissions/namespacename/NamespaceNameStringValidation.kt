package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.permissions.namespacename.NamespaceNameStringValidationResult.Invalid

internal fun validateNamespaceNameString(string: String): Invalid? {
	if (string.isEmpty()) {
		return Invalid.EMPTY
	}

	if (!(string.first().isValidFirstNamespaceNameChar())) {
		return Invalid.INVALID_FIRST_CHAR
	}

	if (!(string.drop(1).dropLast(1).all(Char::isValidMiddleNamespaceNameChar))) {
		return Invalid.INVALID_MIDDLE_CHAR
	}

	if (!(string.last().isValidLastNamespaceNameChar())) {
		return Invalid.INVALID_LAST_CHAR
	}

	return null
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Char.isValidFirstNamespaceNameChar(): Boolean {
	return (this in 'a'..'z')
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Char.isValidMiddleNamespaceNameChar(): Boolean {
	return this.isValidFirstNamespaceNameChar() || (this in 'A'..'Z') || (this in '0'..'9')
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Char.isValidLastNamespaceNameChar(): Boolean {
	return this.isValidMiddleNamespaceNameChar()
}
