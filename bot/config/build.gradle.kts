plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-name"))
	api(project(":bot:command-prefix"))
	api(project(":bot:poll-option"))
	api(project(":bot:environment-type"))
}
