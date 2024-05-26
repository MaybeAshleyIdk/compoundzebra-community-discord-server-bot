plugins {
	`kotlin-dsl`
}

dependencies {
	api(projects.projectStructure.projectType)
	implementation(projects.projectStructure.projectTypePolicies)
}
