plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.eventListening.api)
	api(projects.bot.polls.componentProtocol)
	api(projects.bot.polls.modification)
	api(projects.bot.configSupplier)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
