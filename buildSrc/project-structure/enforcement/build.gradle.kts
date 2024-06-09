plugins {
	`kotlin-dsl`
}

dependencies {
	implementation(projects.projectStructure.projectTypePolicies)
	implementation(projects.projectStructure.tree)
}
