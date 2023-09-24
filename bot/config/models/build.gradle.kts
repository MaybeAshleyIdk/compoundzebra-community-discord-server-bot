plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:models:bot-commands-models-prefix"))
	implementation(project(":bot:models:bot-models-env"))
}
