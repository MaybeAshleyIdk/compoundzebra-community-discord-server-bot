plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-wait:bot-shutdown-wait-api"))

	implementation(project(":bot:bot-shutdown-manager"))

	implementation(libs.javax.inject)
}
