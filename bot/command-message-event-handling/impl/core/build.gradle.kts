plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandMessageEventHandling.api)
	api(projects.bot.commandMessageEventHandling.impl.messageParser)
	api(projects.bot.commandMessageEventHandling.impl.command)
	api(projects.bot.logging)

	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
