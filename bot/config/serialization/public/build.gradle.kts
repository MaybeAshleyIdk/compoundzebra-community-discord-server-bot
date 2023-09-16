plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(project(":bot:config:bot-config-models"))

	implementation(libs.jsr305)

	implementation(libs.okio)
}
