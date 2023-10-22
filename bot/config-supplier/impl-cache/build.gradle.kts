plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-supplier:api"))
	implementation(project(":bot:config"))
	implementation(project(":bot:config-cache"))

	implementation(libs.javax.inject)
}
