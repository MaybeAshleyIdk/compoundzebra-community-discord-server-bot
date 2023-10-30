plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:logging:api"))

	implementation(libs.javax.inject)
}
