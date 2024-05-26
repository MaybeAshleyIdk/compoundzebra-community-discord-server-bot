plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.id)
	api(projects.bot.polls.description)
	api(projects.bot.polls.option)
	api(projects.bot.config)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
