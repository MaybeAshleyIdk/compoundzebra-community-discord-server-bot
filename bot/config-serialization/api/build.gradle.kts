plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.config)
	api(libs.okio)
}
