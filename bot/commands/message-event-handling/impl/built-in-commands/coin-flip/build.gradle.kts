plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.command)
	api(projects.bot.configSupplier)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javax.inject)
}
