plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-holding:api"))
	api(project(":bot:poll-management"))

	implementation(libs.javax.inject)
}
