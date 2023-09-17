plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":snowflake"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
