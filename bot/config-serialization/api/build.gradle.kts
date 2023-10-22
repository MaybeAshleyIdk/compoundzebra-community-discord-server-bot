plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:config"))

	implementation(libs.okio)
}
