plugins {
	buildSrc.projectType.service
	`java-library`
}

dependencies {
	api(projects.TODO.api)
}
