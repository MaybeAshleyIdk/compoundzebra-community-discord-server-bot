plugins {
	buildSrc.projectStructure.`service-interface`

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.id)
	api(projects.bot.polls.details)
}
