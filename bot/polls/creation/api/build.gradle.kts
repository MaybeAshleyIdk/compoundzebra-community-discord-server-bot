plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.newPollDetails)
	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
