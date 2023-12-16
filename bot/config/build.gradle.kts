plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandName)
	api(projects.bot.commandPrefix)
	api(projects.bot.pollOption)
	api(projects.bot.environmentType)
}
