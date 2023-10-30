plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-id"))
	api(project(":bot:poll-details"))
}
