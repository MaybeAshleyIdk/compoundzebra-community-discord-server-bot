plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-manager:api"))

	implementation(libs.javax.inject)
}
