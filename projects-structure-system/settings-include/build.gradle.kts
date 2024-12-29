plugins {
	`kotlin-dsl`
}

dependencies {
	api(projects.includeDsl.api)

	implementation(projects.includeDsl.impl)
	implementation(projects.projectPath)
}
