plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(project(":bot:message-event-handler-mediator"))

	implementation(libs.javax.inject)
}
