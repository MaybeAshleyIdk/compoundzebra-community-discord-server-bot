plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.pollCreation.api)
}
