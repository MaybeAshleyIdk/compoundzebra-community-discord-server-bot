plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.TODO.api)

	implementation(libs.javax.inject)
}
