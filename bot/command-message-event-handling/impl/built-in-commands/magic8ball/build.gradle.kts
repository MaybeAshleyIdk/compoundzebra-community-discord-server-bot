plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:command"))
	api(project(":bot:config-supplier"))
	api(project(":bot:logging"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.javax.inject)
}
