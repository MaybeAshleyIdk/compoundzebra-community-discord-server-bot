plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.command)
	api(projects.bot.configSupplier)
	api(projects.bot.shutdown.requesting)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javaxInject)
}
