plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
