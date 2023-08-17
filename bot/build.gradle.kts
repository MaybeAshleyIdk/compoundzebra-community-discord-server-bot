plugins {
	kotlin("jvm")
	kotlin("kapt")
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

dependencies {
	implementation(project(":logging"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("net.dv8tion:JDA:5.0.0-beta.13") {
		exclude(module = "opus-java")
	}

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}
