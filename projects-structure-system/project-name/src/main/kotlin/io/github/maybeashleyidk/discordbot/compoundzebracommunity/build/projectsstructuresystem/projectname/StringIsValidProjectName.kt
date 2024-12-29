package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname

internal fun String.isValidProjectName(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	if (!(this.all(Char::isValidProjectNameChar))) {
		return false
	}

	if (!(this.first().isValidFirstProjectNameChar())) {
		return false
	}

	if (!(this.last().isValidLastProjectNameChar())) {
		return false
	}

	if ("--" in this) {
		return false
	}

	return true
}

private fun Char.isValidFirstProjectNameChar(): Boolean {
	return (this in 'a'..'z')
}

private fun Char.isValidProjectNameChar(): Boolean {
	return (this in 'a'..'z') || (this == '-') || (this in '0'..'9')
}

private fun Char.isValidLastProjectNameChar(): Boolean {
	return (this in 'a'..'z') || (this in '0'..'9')
}
