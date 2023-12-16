plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.requesting.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javax.inject)
}
