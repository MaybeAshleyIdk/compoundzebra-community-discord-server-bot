import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.conventions.applyConventionsToAllProjects

plugins {
	// The line
	//         kotlin("jvm") version libs.versions.kotlin apply false
	// is omitted because the Kotlin Gradle plugin is already on the classpath because the buildSrc module added it as
	// a dependency.
	// See the issue <https://github.com/gradle/gradle/issues/20084>

	alias(libs.plugins.ksp) apply false

	alias(libs.plugins.ktlint) apply false
	alias(libs.plugins.shadow) apply false
}

applyConventionsToAllProjects()

tasks.wrapper {
	// Download sources and documentation by default.
	distributionType = Wrapper.DistributionType.ALL
}
