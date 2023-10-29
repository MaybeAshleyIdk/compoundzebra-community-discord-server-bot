plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:command-message-event-handling:api"))
	api(project(":bot:command-message-event-handling:impl:message-parser"))
	api(project(":bot:command-message-event-handling:impl:command"))
	api(project(":bot:logging"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
