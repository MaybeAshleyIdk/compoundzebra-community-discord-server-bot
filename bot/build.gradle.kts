plugins {
	`java-library`
}

dependencies {
	api(project(":bot:env"))
	api(project(":bot:token"))
	api(project(":bot:main"))
}
