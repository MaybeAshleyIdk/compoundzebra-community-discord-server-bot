package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByName

internal fun Project.provideLibsVersionCatalog(): Provider<VersionCatalog> {
	return this.provider(this::getLibsVersionCatalog)
}

private fun Project.getLibsVersionCatalog(): VersionCatalog {
	val versionCatalogs: VersionCatalogsExtension =
		this.extensions.getByName<VersionCatalogsExtension>(name = "versionCatalogs")

	return versionCatalogs.named("libs")
}
