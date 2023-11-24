plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:event-listening:api"))
	api(project(":bot:message-event-handler-mediation"))
	api(project(":bot:poll-event-listening"))
	api(project(":bot:private-message-event-handling"))
	api(project(":bot:logging"))

	implementation(libs.javax.inject)
}
