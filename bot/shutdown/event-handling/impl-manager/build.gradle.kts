plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.eventHandling.api)
	api(projects.bot.shutdown.management)
}
