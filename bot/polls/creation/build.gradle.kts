plugins {
	buildSrc.projectStructure.service
	`java-library`
}

dependencies {
	api(projects.bot.polls.creation.api)
}
