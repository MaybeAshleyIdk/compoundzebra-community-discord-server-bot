plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-request:bot-shutdown-request-api"))

	implementation(project(":bot:features:shutdown:manager:bot-features-shutdown-manager-public"))

	implementation(libs.javax.inject)
}
