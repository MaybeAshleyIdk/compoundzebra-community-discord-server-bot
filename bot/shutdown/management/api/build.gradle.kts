plugins {
	buildSrc.projectType.`service-interface`
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbacks)

	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
