plugins {
	buildSrc.projectType.composite
	`java-library`
}

dependencies {
	api(projects.bot.main)
}
