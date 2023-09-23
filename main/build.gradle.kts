plugins {
	application
	kotlin("jvm")

	alias(libs.plugins.ktlint)
}

group = "io.github.maybeashleyidk"
version = "0.1.0-indev08"

application {
	applicationName = "czd-bot"
	mainClass = "io.github.maybeashleyidk.discordbot.compoundzebracommunity.Main"
	executableDir = ""
}

ktlint {
	version = libs.versions.ktlint
	installGitPreCommitHookBeforeBuild = true
}

dependencies {
	implementation(project(":bot"))
}
