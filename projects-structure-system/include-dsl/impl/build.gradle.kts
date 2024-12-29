plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.includeDsl.api)
	api(projects.serviceImplementationName)
}
