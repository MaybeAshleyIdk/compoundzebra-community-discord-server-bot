plugins {
	`kotlin-dsl`
}

dependencies {
	api(projects.projectStructure.projectType)
	api(projects.projectStructure.projectPolicy)
}
