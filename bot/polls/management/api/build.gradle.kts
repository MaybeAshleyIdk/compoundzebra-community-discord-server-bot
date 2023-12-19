plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.newPollDetails)
	api(projects.bot.polls.details)
	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
