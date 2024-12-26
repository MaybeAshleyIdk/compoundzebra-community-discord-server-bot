plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.buildType)
	api(projects.bot.token)

	implementation(projects.bot.wiring)

	implementation(libs.kotlinxCoroutinesCore)
}
