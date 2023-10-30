plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-wait:api"))
	api(project(":bot:shutdown-manager"))

	implementation(libs.javax.inject)
}
