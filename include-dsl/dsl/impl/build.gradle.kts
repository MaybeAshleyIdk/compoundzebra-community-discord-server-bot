plugins {
	`kotlin-dsl`
}

dependencies {
	api(projects.dsl.api)

	implementation(projects.projectName)
}
