plugins {
	buildSrc.projectType.`service-implementation`.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.eventHandling.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javaxInject)
}
