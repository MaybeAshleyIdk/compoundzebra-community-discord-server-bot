package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.internal.packages

import org.gradle.api.file.Directory

@JvmInline
internal value class FullyQualifiedPackageName private constructor(private val names: List<PackageName>) {

	companion object {

		fun ofStringOrNull(fullyQualifiedPackageNameString: String): FullyQualifiedPackageName? {
			val names: List<PackageName> = fullyQualifiedPackageNameString
				.split(".")
				.map(PackageName::ofStringOrNull)
				.nonNullOrNull()
				?.ifEmpty { null }
				?: return null

			return FullyQualifiedPackageName(names)
		}

		fun ofPackageNames(names: List<PackageName>): FullyQualifiedPackageName {
			require(names.isNotEmpty()) {
				"A fully-qualified package name must consist of at least one package name"
			}

			return FullyQualifiedPackageName(names)
		}
	}

	init {
		require(this.names.isNotEmpty()) {
			"A fully-qualified package name must consist of at least one package name"
		}
	}

	val packageNames: List<PackageName>
		get() {
			return this.names
		}

	override fun toString(): String {
		return this.names.joinToString(separator = ".")
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
