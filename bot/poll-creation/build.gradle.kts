plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:poll-creation:api"))
}