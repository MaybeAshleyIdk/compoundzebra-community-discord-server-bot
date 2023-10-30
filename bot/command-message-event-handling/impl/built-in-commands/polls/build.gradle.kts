plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:command"))
	api(project(":bot:config-supplier"))
	api(project(":bot:polls"))

	implementation(project(":bot:utils"))
	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.javax.inject)
}
