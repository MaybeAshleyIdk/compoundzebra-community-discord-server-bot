plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:message-event-handler-mediation:api"))
}