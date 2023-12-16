plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.shutdownCallbackRegistry.api)
}
