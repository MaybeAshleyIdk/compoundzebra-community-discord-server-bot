plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	implementation(project(":bot:bot-command-prefix"))
	implementation(project(":bot:commands:impl:bot-commands-impl-base"))
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-config-supplier"))
	implementation(project(":bot:bot-logging"))
	implementation(project(":bot:bot-utils"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
