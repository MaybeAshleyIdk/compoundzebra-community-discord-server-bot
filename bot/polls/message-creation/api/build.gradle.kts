plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(projects.bot.polls.id)
	api(projects.bot.polls.details)
}
