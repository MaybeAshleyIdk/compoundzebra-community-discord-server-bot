plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-source:api"))
	api(project(":bot:config-serialization"))
	api(project(":bot:environment-type"))

	implementation(libs.javax.inject)
}
