plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:environment-type"))
	api(project(":bot:token"))

	implementation(project(":bot:wiring"))
}
