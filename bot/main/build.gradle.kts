plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:models:bot-models-env"))
	implementation(project(":bot:models:bot-models-token"))
	implementation(project(":bot:bot-wiring"))

	implementation(project(":bot:logging:bot-logging-public"))
	implementation(project(":bot:features:shutdown:wait:bot-features-shutdown-wait-public"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
}
