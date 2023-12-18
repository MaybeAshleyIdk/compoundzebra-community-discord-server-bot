plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.id)

	implementation(libs.javaxInject)
}
