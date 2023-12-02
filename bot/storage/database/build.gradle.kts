plugins {
	buildSrc.projectStructure.service
	`java-library`
}

dependencies {
	api(projects.bot.storage.database.api)
}
