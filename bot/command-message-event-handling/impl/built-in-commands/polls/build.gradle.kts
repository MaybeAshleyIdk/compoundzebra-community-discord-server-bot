plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.command)
	api(projects.bot.configSupplier)
	api(projects.bot.pollCreation)
	api(projects.bot.pollHolding)
	api(projects.bot.pollComponentProtocol)

	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javax.inject)
}
