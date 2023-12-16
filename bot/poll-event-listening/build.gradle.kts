plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.pollEventListening.api)
}
