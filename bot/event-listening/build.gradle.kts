plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.eventListening.api)
}
