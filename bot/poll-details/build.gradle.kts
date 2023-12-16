plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollId)
	api(projects.bot.pollDescription)
	api(projects.bot.pollOption)
	api(projects.bot.config)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
