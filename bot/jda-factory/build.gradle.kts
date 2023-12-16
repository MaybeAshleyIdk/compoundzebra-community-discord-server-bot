plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(projects.bot.eventListening)

	implementation(libs.javax.inject)
}
