plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commands.commandName)
	api(libs.jda) {
		exclude(module = "opus-opus")
	}
}
