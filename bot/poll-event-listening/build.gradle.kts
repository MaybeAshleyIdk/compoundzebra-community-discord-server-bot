plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:poll-event-listening:api"))
}
