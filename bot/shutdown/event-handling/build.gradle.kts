plugins {
	buildSrc.projectStructure.service
	`java-library`
}

dependencies {
	api(projects.bot.shutdown.eventHandling.api)
}
