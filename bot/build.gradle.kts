plugins {
	buildSrc.projectStructure.composite
	`java-library`
}

dependencies {
	api(projects.bot.main)
}
