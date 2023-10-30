plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:command-message-event-handling:api"))
}
