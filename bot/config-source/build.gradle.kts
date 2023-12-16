plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.configSource.api)
}
