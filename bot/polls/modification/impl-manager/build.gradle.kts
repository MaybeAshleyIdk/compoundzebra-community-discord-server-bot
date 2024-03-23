plugins {
	buildSrc.projectType.`service-implementation`.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.modification.api)
	api(projects.bot.polls.management)

	implementation(libs.javaxInject)
}
