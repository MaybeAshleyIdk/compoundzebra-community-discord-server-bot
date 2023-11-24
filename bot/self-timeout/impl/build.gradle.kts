plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:self-timeout:api"))
	api(project(":bot:logging"))

	implementation(project(":bot:utils"))
	implementation(project(":bot:utils-coroutines"))
	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
