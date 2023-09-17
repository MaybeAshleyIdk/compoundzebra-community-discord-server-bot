plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-snowflake"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
