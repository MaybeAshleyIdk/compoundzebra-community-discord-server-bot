plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(`jda-without-opusJava`)
	api(projects.bot.eventListening)

	implementation(libs.javaxInject)
}
