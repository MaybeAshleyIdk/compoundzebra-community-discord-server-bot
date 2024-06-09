plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.commandName)
	api(`jda-without-opusJava`)
}
