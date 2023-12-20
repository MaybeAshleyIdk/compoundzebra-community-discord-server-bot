plugins {
	buildSrc.projectType.`service-implementation`.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbackRegistration.api)
	api(projects.bot.shutdown.management)

	implementation(libs.javaxInject)
}
