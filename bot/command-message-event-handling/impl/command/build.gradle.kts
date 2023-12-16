plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandName)
	api(libs.jda) {
		exclude(module = "opus-opus")
	}
}
