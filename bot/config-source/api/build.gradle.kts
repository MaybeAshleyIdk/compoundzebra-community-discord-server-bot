plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.config)
}
