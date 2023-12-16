plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.messageEventHandling.api)
	api(projects.bot.commands.messageEventHandling.impl.messageParser)
	api(projects.bot.commands.messageEventHandling.impl.command)
	api(projects.bot.logging)

	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}