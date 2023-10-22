plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-request:bot-shutdown-request-api"))

	implementation(project(":bot:bot-shutdown-manager"))

	implementation(libs.javax.inject)
}
