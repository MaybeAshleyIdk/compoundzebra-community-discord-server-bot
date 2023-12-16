plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.commandMessageEventHandling.api)
}
