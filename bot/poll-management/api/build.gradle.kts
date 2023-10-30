plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-details"))
	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
