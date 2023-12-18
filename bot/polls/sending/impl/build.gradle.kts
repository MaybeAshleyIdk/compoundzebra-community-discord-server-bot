plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.sending.api)
	api(projects.bot.polls.creation)
	api(projects.bot.polls.componentProtocol)
	api(projects.bot.configSupplier)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javaxInject)
}
