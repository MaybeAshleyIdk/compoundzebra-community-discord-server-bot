plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(project(":bot:message-event-handler-mediation"))
	api(project(":bot:poll-event-listening"))
	api(project(":bot:private-message-event-handling"))

	implementation(libs.javax.inject)
}
