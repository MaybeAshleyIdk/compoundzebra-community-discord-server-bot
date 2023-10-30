plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-name"))
	api(libs.jda) {
		exclude(module = "opus-opus")
	}
}
