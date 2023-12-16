plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.configCache.api)
}
