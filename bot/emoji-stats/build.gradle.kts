plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.emojiStats.api)
}
