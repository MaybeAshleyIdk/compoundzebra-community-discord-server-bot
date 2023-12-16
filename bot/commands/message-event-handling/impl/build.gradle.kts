plugins {
	`java-library`
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.core)
}
