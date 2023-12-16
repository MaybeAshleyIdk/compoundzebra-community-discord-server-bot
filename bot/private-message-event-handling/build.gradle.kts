plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.privateMessageEventHandling.api)
}
