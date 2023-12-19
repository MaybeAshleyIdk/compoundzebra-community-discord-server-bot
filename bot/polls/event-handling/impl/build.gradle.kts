plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.eventHandling.api)
	api(projects.bot.polls.creation)
	api(projects.bot.polls.messageCreation)
	api(projects.bot.polls.componentProtocol)
	api(projects.bot.polls.modification)
	api(projects.bot.configSupplier)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
