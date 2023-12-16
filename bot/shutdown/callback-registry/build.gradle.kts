plugins {
	`java-library`
}

dependencies {
	api(projects.bot.shutdown.callbackRegistry.api)
}
