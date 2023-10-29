plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:token"))
	implementation(project(":bot:message-event-handler-mediator"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
