plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-callback-registry:api"))
	api(project(":bot:shutdown-manager"))

	implementation(libs.javax.inject)
}
