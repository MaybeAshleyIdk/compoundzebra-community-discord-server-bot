plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:features:shutdown:bot-features-shutdown-public"))

	implementation(libs.javax.inject)
}
