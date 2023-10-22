plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-request:api"))

	implementation(project(":bot:shutdown-manager"))

	implementation(libs.javax.inject)
}
