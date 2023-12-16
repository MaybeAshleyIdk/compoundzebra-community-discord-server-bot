plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollDetails)
	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
