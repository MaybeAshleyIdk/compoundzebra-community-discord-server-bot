plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdownCallbacks)

	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
