plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.logging.api)
}
