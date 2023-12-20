plugins {
	buildSrc.projectType.`service-implementation`.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configCache.api)
	api(projects.bot.configSource)
	api(projects.bot.logging)

	implementation(libs.javaxInject)
}
