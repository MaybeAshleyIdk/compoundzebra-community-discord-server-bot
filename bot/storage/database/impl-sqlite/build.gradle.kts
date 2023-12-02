plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:storage:database:api"))
	api(project(":bot:shutdown-callback-registry"))
	api(project(":bot:shutdown-request"))
	api(project(":bot:logging"))

	implementation(project(":bot:utils-coroutines"))
	implementation(libs.kotlinx.coroutines.core)
	implementation(libs.okio)
	implementation(libs.sqlite.jdbc)

	implementation(libs.javax.inject)
}
