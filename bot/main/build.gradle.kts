plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:bot-models"))
	implementation(project(":bot:bot-wiring"))

	implementation(project(":bot:logging:bot-logging-public"))
	implementation(project(":bot:features:shutdown:bot-features-shutdown-public"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
