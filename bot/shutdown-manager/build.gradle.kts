plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.shutdownManager.api)
}
