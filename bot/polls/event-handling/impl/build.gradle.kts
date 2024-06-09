plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.eventHandling.api)
	api(projects.bot.polls.componentProtocol)
	api(projects.bot.polls.modification)
	api(projects.bot.configSupplier)

	implementation(projects.bot.utils.coroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
