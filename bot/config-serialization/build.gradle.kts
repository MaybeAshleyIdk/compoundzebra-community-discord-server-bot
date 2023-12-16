plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.configSerialization.api)
}
