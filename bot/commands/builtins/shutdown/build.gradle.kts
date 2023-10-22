plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	implementation(project(":bot:commands:bot-commands-base"))
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-shutdown-request"))
	implementation(project(":bot:bot-config-supplier"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
