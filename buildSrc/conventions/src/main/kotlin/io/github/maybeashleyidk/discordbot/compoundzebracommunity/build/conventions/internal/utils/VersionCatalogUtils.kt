package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.utils

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import java.util.Optional

internal fun Provider<VersionCatalog>.requireLibrary(alias: String): Provider<MinimalExternalModuleDependency> {
	return this
		.flatMap { versionCatalog: VersionCatalog ->
			versionCatalog.requireLibrary(alias)
		}
}

private fun VersionCatalog.requireLibrary(alias: String): Provider<MinimalExternalModuleDependency> {
	val optionalLibraryProvider: Optional<Provider<MinimalExternalModuleDependency>> = this.findLibrary(alias)

	return optionalLibraryProvider
		.orElseThrow {
			IllegalStateException("No library found with the alias \"$alias\"")
		}
}
