plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.request.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javax.inject)
}
