plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-wait:bot-shutdown-wait-api"))

	implementation(project(":bot:features:shutdown:manager:bot-features-shutdown-manager-public"))

	implementation(libs.javax.inject)
}
