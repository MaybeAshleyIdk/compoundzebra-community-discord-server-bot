plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-id"))

	implementation(libs.javax.inject)
}
