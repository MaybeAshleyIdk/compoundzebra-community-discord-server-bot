plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configSupplier.api)
	api(projects.bot.configCache)

	implementation(libs.javax.inject)
}
