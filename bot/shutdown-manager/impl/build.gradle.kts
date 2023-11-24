plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:shutdown-manager:api"))
	api(project(":bot:logging"))

	implementation(project(":bot:utils-coroutines"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
