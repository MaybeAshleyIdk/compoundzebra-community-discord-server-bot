plugins {
	`java-library`
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.wiring)
	api(projects.bot.commands.messageEventHandling.impl.eventHandlerImpl)
}
