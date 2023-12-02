plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:storage:database:api"))
}
