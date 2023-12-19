plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.id)
	api(projects.bot.polls.details)
	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
