plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.command)
	api(projects.bot.configSupplier)
	api(projects.bot.polls.creation)
	api(projects.bot.polls.holding)
	api(projects.bot.polls.componentProtocol)

	implementation(projects.bot.utils.strings)
	implementation(projects.bot.utils.coroutinesJda)

	implementation(libs.javaxInject)
}
