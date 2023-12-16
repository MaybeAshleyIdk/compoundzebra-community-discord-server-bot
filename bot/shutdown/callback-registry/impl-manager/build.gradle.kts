plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbackRegistry.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javax.inject)
}
