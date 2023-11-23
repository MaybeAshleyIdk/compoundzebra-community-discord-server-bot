plugins {
	ApiImplWiringProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:self-timeout:api"))
}
