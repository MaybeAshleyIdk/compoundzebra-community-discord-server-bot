plugins {
	ApiImplWiringProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:storage:guild-member:api"))
}
