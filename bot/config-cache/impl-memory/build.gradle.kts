plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-cache:api"))
	implementation(project(":bot:config"))
	implementation(project(":bot:config-source"))
	implementation(project(":bot:logging"))

	implementation(libs.javax.inject)
}
