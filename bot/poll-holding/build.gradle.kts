plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:poll-holding:api"))
}