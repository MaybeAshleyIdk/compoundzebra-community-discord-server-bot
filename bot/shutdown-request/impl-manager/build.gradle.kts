plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-request:api"))
	api(project(":bot:shutdown-manager"))

	implementation(libs.javax.inject)
}
