plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:bot-config"))

	implementation(libs.okio)
}
