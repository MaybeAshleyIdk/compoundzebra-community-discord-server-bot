plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.conditionalMessageEventHandling.api)
}
