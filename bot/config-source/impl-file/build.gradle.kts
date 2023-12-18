plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configSource.api)
	api(projects.bot.configSerialization)
	api(projects.bot.environmentType)

	implementation(libs.javaxInject)
}
