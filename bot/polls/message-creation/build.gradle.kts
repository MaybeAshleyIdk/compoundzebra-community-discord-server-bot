plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.messageCreation.api)
}
