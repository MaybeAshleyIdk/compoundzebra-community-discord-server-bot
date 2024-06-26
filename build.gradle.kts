import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.applyConventionsToAllProjects
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectstructure.subprojectcreation.SubprojectCreationTask

plugins {
	// The line
	//         kotlin("jvm") version libs.versions.kotlin apply false
	// is omitted because the Kotlin Gradle plugin is already on the classpath because the buildSrc module added it as
	// a dependency.
	// See the issue <https://github.com/gradle/gradle/issues/20084>

	alias(libs.plugins.ksp) apply false

	alias(libs.plugins.ktlint) apply false
	alias(libs.plugins.shadow) apply false

	buildSrc.projectStructure.namespace
}

applyConventionsToAllProjects()

tasks.wrapper {
	// Download sources and documentation by default.
	distributionType = Wrapper.DistributionType.ALL
}

tasks.register<SubprojectCreationTask>("createModule") {
	group = "modulization"

	basePackageName = "io.github.maybeashleyidk.discordbot.compoundzebracommunity"
	packageIgnoredProjectPathPrefixes =
		listOf(
			":bot",
			":main",
		)
}
