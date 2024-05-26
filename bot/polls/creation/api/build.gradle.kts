plugins {
	buildSrc.projectStructure.`service-interface`

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.id)
	api(projects.bot.polls.description)
	api(projects.bot.polls.option)
	api(projects.bot.config)
	api(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(projects.bot.polls.details)
}
