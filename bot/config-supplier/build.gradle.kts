plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(project(":bot:config-supplier:api"))
}
