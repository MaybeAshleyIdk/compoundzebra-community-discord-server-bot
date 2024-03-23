plugins {
	buildSrc.projectType.service
	`java-library`
}

dependencies {
	api(projects.bot.commands.messageEventHandling.api)
}
