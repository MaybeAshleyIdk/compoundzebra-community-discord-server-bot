plugins {
	buildSrc.projectStructure.`service-interface`

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbacks)

	implementation(libs.kotlinxCoroutinesCore)
}
