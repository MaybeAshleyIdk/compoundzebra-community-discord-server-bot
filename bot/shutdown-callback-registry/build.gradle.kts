plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:shutdown-callback-registry:api"))
}
