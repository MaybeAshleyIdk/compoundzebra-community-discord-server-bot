plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configSupplier)

	implementation(libs.javaxInject)
}
