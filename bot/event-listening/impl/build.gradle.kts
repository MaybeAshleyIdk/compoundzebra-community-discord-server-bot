plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.eventListening.api)
	api(projects.bot.shutdown.callbackRegistration)
	api(projects.bot.shutdown.eventHandling)
	api(projects.bot.messageEventHandlerMediation)
	api(projects.bot.polls.eventListening)
	api(projects.bot.privateMessageEventHandling)
	api(projects.bot.logging)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
