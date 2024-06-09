plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.experimental.permissions.id)
	api(projects.bot.experimental.permissions.resource)

	implementation(projects.bot.utils.sequences)
}
