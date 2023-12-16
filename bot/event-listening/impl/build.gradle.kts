plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.eventListening.api)
	api(projects.bot.shutdownCallbackRegistry)
	api(projects.bot.shutdownEventHandling)
	api(projects.bot.messageEventHandlerMediation)
	api(projects.bot.pollEventListening)
	api(projects.bot.privateMessageEventHandling)
	api(projects.bot.logging)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
