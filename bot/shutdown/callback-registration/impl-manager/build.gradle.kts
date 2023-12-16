plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbackRegistration.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javax.inject)
}
