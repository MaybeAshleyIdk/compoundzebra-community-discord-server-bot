plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.messageEventHandling.api)
	api(projects.bot.commands.messageEventHandling.impl.messageParser)
	api(projects.bot.commands.messageEventHandling.impl.command)
	api(projects.bot.logging)

	implementation(projects.bot.utils.strings)
	implementation(projects.bot.utils.coroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)
}
