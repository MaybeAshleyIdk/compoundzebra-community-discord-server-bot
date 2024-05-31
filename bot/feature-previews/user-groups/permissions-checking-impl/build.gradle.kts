plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.experimental.permissions.checkingApi)

	implementation(libs.javaxInject)
}
