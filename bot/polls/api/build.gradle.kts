plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:config"))
	implementation(project(":bot:snowflake"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
