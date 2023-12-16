plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.shutdownRequest.api)
}
