plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:shutdown-wait:api"))
}
