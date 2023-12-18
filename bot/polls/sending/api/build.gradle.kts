plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(projects.bot.polls.description)
	api(projects.bot.polls.option)
}
