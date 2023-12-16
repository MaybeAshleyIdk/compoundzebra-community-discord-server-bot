plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollModification.api)
	api(projects.bot.pollManagement)

	implementation(libs.javax.inject)
}
