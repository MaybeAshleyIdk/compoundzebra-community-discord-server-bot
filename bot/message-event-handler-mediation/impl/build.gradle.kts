plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:message-event-handler-mediation:api"))
	api(project(":bot:command-message-event-handling"))
	api(project(":bot:conditional-message-event-handling"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
