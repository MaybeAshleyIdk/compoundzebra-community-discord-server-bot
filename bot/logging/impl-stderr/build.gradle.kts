plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.logging.api)

	implementation(libs.javaxInject)
}
