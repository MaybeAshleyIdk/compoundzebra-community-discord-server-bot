plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.projectName)
	api(projects.serviceImplementationName)
}