plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:features:shutdown:wait:bot-features-shutdown-wait-public"))

	implementation(project(":bot:features:shutdown:manager:bot-features-shutdown-manager-public"))

	implementation(libs.javax.inject)
}
