plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:config:bot-config-models"))

	implementation(libs.okio)
}
