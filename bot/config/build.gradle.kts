plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:command-name"))
	implementation(project(":bot:command-prefix"))
	implementation(project(":bot:environment-type"))
}
