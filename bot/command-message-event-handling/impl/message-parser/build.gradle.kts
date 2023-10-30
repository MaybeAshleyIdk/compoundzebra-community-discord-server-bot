plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-supplier"))

	implementation(libs.javax.inject)
}
