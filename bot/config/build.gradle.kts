plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.commandName)
	api(projects.bot.commands.prefix)
	api(projects.bot.pollOption)
	api(projects.bot.environmentType)
}
