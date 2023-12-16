plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.pollModification.api)
}
