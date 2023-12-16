plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollId)

	implementation(libs.javax.inject)
}
