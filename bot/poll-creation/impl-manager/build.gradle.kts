plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-creation:api"))
	api(project(":bot:poll-management"))

	implementation(libs.javax.inject)
}
