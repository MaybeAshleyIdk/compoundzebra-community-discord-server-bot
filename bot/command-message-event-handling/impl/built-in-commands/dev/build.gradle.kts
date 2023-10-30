plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:command"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.javax.inject)
}
