plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:self-timeout:api"))
}
