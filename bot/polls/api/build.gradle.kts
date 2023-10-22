plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-snowflake"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
