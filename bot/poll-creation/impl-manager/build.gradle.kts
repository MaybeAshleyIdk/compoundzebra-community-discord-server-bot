plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollCreation.api)
	api(projects.bot.pollManagement)

	implementation(libs.javax.inject)
}
