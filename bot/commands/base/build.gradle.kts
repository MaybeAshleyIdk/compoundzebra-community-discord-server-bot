plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:command-name"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
