plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-modification:api"))
	api(project(":bot:poll-management"))

	implementation(libs.javax.inject)
}
