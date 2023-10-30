plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-event-listening:api"))
	api(project(":bot:poll-component-protocol"))
	api(project(":bot:poll-modification"))
	api(project(":bot:config-supplier"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
