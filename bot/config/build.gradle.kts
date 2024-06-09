plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.commandName)
	api(projects.bot.commands.prefix)
	api(projects.bot.polls.option)
	api(projects.bot.environmentType)
}
