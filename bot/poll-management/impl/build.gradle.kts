plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-management:api"))
	api(project(":bot:logging"))

	implementation(project(":bot:snowflake-generator"))
	implementation(project(":bot:utils"))
	implementation(project(":bot:utils-coroutines"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
