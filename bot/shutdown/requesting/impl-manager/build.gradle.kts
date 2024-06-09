plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.requesting.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javaxInject)
}
