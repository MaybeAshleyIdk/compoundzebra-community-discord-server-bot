plugins {
	kotlin("jvm")
	application

	id("io.github.mfederczuk.ktlint")
}

group = "io.github.maybeashleyidk"
version = "0.1.0-indev05"

application {
	applicationName = "czd-bot"
	mainClass = "io.github.maybeashleyidk.discordbot.compoundzebracommunity.Main"
	executableDir = ""
}

ktlint {
	version = "0.50.0"
	installGitPreCommitHookBeforeBuild = true
}

dependencies {
	implementation(project(":bot"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")
}
