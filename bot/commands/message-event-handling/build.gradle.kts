plugins {
	buildSrc.projectStructure.service
	`java-library`
}

dependencies {
	api(projects.bot.commands.messageEventHandling.api)
}
