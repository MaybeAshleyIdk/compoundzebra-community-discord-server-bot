plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:event-listening:api"))
}
