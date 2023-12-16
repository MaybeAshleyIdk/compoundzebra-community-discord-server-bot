plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollId)
	api(projects.bot.pollDescription)
	api(projects.bot.pollOption)
	api(projects.bot.config)
	api(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(projects.bot.pollDetails)
}
