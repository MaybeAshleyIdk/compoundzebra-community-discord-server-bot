plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:private-message-event-handling:api"))
}
