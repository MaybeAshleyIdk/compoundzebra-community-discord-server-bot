plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:conditional-message-event-handling:api"))
}
