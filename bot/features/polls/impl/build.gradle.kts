plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:features:polls:bot-features-polls-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-config-supplier"))
	implementation(project(":bot:bot-snowflake"))
	implementation(project(":bot:bot-logging"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
