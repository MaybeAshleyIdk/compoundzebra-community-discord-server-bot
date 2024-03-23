plugins {
	buildSrc.projectType.`service-implementation`.composite
	`java-library`
}

dependencies {
	api(projects.TODO)
}
