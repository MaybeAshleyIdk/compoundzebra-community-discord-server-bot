plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:command"))
	api(project(":bot:config-supplier"))
	api(project(":bot:poll-creation"))
	api(project(":bot:poll-holding"))
	api(project(":bot:poll-component-protocol"))

	implementation(project(":bot:utils"))
	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.javax.inject)
}
