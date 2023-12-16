plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdownRequest.api)
	api(projects.bot.shutdownManager)

	implementation(libs.javax.inject)
}
