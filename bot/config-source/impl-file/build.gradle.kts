plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-source:api"))
	implementation(project(":bot:config"))
	implementation(project(":bot:config-serialization"))
	implementation(project(":bot:environment-type"))

	implementation(libs.okio)

	implementation(libs.javax.inject)
}
