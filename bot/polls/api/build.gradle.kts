plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config"))
	api(project(":bot:snowflake"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
