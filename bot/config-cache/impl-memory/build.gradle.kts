plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-cache:api"))
	api(project(":bot:config-source"))
	api(project(":bot:logging"))

	implementation(libs.javax.inject)
}
