plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollId)
	api(projects.bot.pollDetails)
}
