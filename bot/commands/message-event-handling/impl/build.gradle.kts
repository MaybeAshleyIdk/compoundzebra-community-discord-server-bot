plugins {
	buildSrc.projectStructure.`service-implementation`.composite
	`java-library`
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.eventHandlerImplFactory)
}
