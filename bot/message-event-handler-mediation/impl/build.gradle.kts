plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.messageEventHandlerMediation.api)
	api(projects.bot.commands.messageEventHandling)
	api(projects.bot.conditionalMessageEventHandling)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
