plugins {
	buildSrc.projectType.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configSupplier)

	implementation(libs.javaxInject)
}
