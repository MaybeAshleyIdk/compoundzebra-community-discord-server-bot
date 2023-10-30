plugins {
	ApiProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config"))
}
