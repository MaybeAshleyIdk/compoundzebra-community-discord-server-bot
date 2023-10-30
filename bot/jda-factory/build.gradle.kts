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

	implementation(libs.javax.inject)
}
