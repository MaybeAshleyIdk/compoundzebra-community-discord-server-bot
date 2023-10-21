plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	implementation(project(":bot:bot-command-prefix"))
	implementation(project(":bot:bot-env"))
}
