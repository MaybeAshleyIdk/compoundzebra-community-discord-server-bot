plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.eventHandling.api)
	api(projects.bot.shutdown.manager)

	implementation(libs.javax.inject)
}
