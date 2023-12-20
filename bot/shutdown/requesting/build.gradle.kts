plugins {
	buildSrc.projectType.service
	`java-library`
}

dependencies {
	api(projects.bot.shutdown.requesting.api)
}
