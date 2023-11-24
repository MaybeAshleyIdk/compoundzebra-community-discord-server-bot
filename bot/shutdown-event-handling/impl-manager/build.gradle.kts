plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-event-handling:api"))
	api(project(":bot:shutdown-manager"))

	implementation(libs.javax.inject)
}
