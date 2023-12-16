plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.holding.api)
	api(projects.bot.polls.management)

	implementation(libs.javax.inject)
}
