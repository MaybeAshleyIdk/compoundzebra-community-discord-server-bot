plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:bot-env"))
	implementation(project(":bot:bot-token"))
	implementation(project(":bot:bot-wiring"))

	implementation(project(":bot:bot-logging"))
	implementation(project(":bot:bot-shutdown-wait"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
}
