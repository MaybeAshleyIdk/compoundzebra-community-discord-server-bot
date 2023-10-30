plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:shutdown-request:api"))
}
