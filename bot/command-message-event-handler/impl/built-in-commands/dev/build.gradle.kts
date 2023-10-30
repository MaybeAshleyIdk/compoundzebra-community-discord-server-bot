plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-message-event-handler:impl:command"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.javax.inject)
}
