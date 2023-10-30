plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(project(":bot:environment-type"))
	api(project(":bot:token"))
	api(project(":bot:logging"))
	api(project(":bot:shutdown-wait"))

	implementation(project(":bot:jda-factory"))

	implementation(project(":bot:logging:wiring"))
	implementation(project(":bot:config-supplier:wiring"))
	implementation(project(":bot:config-cache:wiring"))
	implementation(project(":bot:config-source:wiring"))
	implementation(project(":bot:config-serialization:wiring"))
	implementation(project(":bot:emoji-stats:wiring"))
	implementation(project(":bot:poll-management:wiring"))
	implementation(project(":bot:poll-creation:wiring"))
	implementation(project(":bot:poll-holding:wiring"))
	implementation(project(":bot:poll-modification:wiring"))
	implementation(project(":bot:poll-event-listening:wiring"))
	implementation(project(":bot:shutdown-manager:wiring"))
	implementation(project(":bot:shutdown-wait:wiring"))
	implementation(project(":bot:shutdown-request:wiring"))
	implementation(project(":bot:command-message-event-handling:wiring"))
	implementation(project(":bot:conditional-message-event-handling:wiring"))
	implementation(project(":bot:message-event-handler-mediation:wiring"))

	implementation(libs.moshi)

	api(libs.dagger)
	ksp(libs.dagger.compiler)
}
