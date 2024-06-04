plugins {
	buildSrc.projectStructure.service
	`java-library`
}

dependencies {
	api(projects.bot.experimental.recentMessages.api)
}
