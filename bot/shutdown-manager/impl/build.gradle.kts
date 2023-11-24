plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-manager:api"))
	api(project(":bot:logging"))

	implementation(libs.javax.inject)
}
