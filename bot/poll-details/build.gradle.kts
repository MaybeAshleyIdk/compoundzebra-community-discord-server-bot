plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-id"))
	api(project(":bot:poll-description"))
	api(project(":bot:poll-option"))
	api(project(":bot:config"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
