plugins {
	buildSrc.projectType.`service-interface`
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbacks)

	implementation(libs.kotlinxCoroutinesCore)
}
