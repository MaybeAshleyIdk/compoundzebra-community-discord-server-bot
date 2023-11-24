plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:shutdown-event-handling:api"))
}
