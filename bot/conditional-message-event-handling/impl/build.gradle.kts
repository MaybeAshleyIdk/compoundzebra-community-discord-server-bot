plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:conditional-message-event-handling:api"))
	api(project(":bot:config-supplier"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
