plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.messageEventHandlerMediation.api)
}
