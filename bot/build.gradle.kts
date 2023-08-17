plugins {
	kotlin("jvm")
	application

	id("io.github.mfederczuk.ktlint")
}

group = "io.github.maybeashleyidk"
version = "0.1.0-indev01"

val javaCompatibilityVersion: JavaVersion = JavaVersion.VERSION_17

java {
	sourceCompatibility = javaCompatibilityVersion
	targetCompatibility = javaCompatibilityVersion
	toolchain.languageVersion.set(JavaLanguageVersion.of(javaCompatibilityVersion.majorVersion))
}

kotlin {
	jvmToolchain(javaCompatibilityVersion.majorVersion.toInt())
}

application {
	mainClass.set("io.github.maybeashleyidk.discordbot.compoundzebracommunity.Main")
}

ktlint {
	version = "0.50.0"
	installGitPreCommitHookBeforeBuild = true
}
