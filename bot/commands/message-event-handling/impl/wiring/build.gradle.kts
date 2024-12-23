plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.messageParser)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommandsWiring)
	api(projects.bot.commands.messageEventHandling.impl.predefinedResponseCommand)
	api(projects.bot.commands.messageEventHandling.impl.eventHandlerImpl)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
