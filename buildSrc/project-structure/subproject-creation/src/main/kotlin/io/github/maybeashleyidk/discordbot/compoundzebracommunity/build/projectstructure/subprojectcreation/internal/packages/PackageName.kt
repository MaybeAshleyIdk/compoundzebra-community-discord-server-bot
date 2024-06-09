package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.packages

import org.gradle.api.file.Directory

@JvmInline
internal value class PackageName private constructor(private val string: String) {

	companion object {

		fun ofStringOrNull(nameString: String): PackageName? {
			if (!(nameString.isValidPackageName())) {
				return null
			}

			return PackageName(nameString)
		}
	}

	init {
		require(this.string.isValidPackageName()) {
			"Invalid package name \"${this.string}\""
		}
	}

	override fun toString(): String {
		return this.string
	}
}

internal fun Directory.dir(packageName: PackageName): Directory {
	return this.dir(packageName.toString())
}

private fun String.isValidPackageName(): Boolean {
	if (this.isEmpty()) {
		return false
	}

	if (!(this.all(Char::isValidPackageNameChar))) {
		return false
	}

	if (!(this.first().isValidFirstPackageNameChar())) {
		return false
	}

	if (!(this.last().isValidLastPackageNameChar())) {
		return false
	}

	return true
}

private fun Char.isValidFirstPackageNameChar(): Boolean {
	return (this in 'a'..'z')
}

private fun Char.isValidPackageNameChar(): Boolean {
	return (this in 'a'..'z') || (this in '0'..'9')
}

private fun Char.isValidLastPackageNameChar(): Boolean {
	return (this in 'a'..'z') || (this in '0'..'9')
}
