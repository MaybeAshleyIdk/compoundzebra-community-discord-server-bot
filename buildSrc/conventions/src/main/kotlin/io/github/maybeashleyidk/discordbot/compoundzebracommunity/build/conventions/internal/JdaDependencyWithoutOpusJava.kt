package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.internal.utils.requireLibrary
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.exclude

internal fun Project.provideJdaDependencyWithoutOpusJava(): Provider<MinimalExternalModuleDependency> {
	return this.provideLibsVersionCatalog()
		.requireLibrary(alias = "jda")
		.map { jdaDependency: MinimalExternalModuleDependency ->
			jdaDependency.copy().exclude(module = "opus-java")
		}
}
