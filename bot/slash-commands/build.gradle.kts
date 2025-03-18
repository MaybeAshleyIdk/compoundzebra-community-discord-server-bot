plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(`jda-without-opusJava`)
	api(projects.bot.genericEventHandler)


	api(projects.bot.configSupplier)

	implementation(projects.bot.utils.coroutinesJda)
	implementation(libs.kotlinxCoroutinesCore)
}
