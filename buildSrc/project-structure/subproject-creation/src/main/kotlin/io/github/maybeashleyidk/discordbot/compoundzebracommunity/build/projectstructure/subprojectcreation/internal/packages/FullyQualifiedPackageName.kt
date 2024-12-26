package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.packages

import org.gradle.api.file.Directory

@JvmInline
internal value class FullyQualifiedPackageName private constructor(val packageNames: List<PackageName>) {

	init {
		require(this.packageNames.isNotEmpty()) {
			"A fully-qualified package name must consist of at least one package name"
		}
	}

	override fun toString(): String {
		return this.packageNames.joinToString(separator = ".")
	}

	companion object {

		fun ofString(fullyQualifiedPackageNameString: String): FullyQualifiedPackageName? {
			val names: List<PackageName> = fullyQualifiedPackageNameString
				.split(".")
				.map(PackageName::ofStringOrNull)
				.nonNullOrNull()
				?.ifEmpty { null }
				?: return null

			return FullyQualifiedPackageName(names)
		}

		fun ofPackageNames(names: List<PackageName>): FullyQualifiedPackageName? {
			if (names.isEmpty()) {
				return null
			}

			return FullyQualifiedPackageName(names)
		}
	}
}

internal fun Directory.dir(fullyQualifiedPackageName: FullyQualifiedPackageName): Directory {
	return fullyQualifiedPackageName.packageNames
		.fold(initial = this, Directory::dir)
}

private fun <T : Any> List<T?>.nonNullOrNull(): List<T>? {
	if (this.any { element: T? -> (element == null) }) {
		return null
	}

	@Suppress("UNCHECKED_CAST")
	return (this as List<T>)
}
