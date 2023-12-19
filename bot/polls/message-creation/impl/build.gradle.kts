plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.messageCreation.api)
	api(projects.bot.polls.componentProtocol)
	api(projects.bot.configSupplier)

	implementation(libs.javaxInject)
}
