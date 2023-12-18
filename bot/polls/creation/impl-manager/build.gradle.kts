plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.creation.api)
	api(projects.bot.polls.management)

	implementation(libs.javaxInject)
}
