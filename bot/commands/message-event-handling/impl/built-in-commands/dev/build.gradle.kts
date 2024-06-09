plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.command)

	implementation(projects.bot.utils.coroutinesJda)

	implementation(libs.javaxInject)
}
