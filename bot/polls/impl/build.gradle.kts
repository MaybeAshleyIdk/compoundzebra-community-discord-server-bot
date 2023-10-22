plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:polls:bot-polls-api"))
	implementation(project(":bot:bot-config"))
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
