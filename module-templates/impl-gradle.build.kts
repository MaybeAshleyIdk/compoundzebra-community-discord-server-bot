plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project("${TODO()}:api"))

	implementation(libs.javax.inject)
}
