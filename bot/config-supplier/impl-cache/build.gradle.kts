plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-supplier:api"))
	api(project(":bot:config-cache"))

	implementation(libs.javax.inject)
}
