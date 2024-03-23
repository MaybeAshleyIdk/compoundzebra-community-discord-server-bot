plugins {
	buildSrc.projectType.`service-interface`
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.genericEventHandler)
}
