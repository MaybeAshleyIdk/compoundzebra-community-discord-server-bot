plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(`jda-without-opusJava`)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(projects.bot.utils.strings)
	implementation(projects.bot.utils.coroutinesJda)
}
