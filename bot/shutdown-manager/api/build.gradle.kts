plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-callbacks"))

	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
