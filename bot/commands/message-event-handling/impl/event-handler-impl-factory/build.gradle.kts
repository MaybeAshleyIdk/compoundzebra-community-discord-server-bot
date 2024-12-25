plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configSupplier)
	// api(projects.bot.emojiStats)
	api(projects.bot.polls.creation)
	api(projects.bot.polls.holding)
	api(projects.bot.selfTimeout)
	api(projects.bot.shutdown.requesting)
	api(projects.bot.environmentType)
	api(projects.bot.logging)

	implementation(projects.bot.commands.messageEventHandling.impl.messageParser)
	implementation(projects.bot.commands.messageEventHandling.impl.builtInCommandsFactory)
	implementation(projects.bot.commands.messageEventHandling.impl.predefinedResponseCommand)
	implementation(projects.bot.commands.messageEventHandling.impl.eventHandlerImpl)
}
