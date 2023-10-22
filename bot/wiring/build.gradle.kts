plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-env"))
	implementation(project(":bot:bot-token"))

	implementation(project(":bot:logging:bot-logging-wiring"))
	implementation(project(":bot:config-supplier:bot-config-supplier-wiring"))
	implementation(project(":bot:config-cache:bot-config-cache-wiring"))
	implementation(project(":bot:config-source:bot-config-source-wiring"))
	implementation(project(":bot:config-serialization:bot-config-serialization-wiring"))
	implementation(project(":bot:emojistats:bot-emojistats-wiring"))
	implementation(project(":bot:polls:bot-polls-wiring"))
	implementation(project(":bot:shutdown-manager:bot-shutdown-manager-wiring"))
	implementation(project(":bot:shutdown-wait:bot-shutdown-wait-wiring"))
	implementation(project(":bot:shutdown-request:bot-shutdown-request-wiring"))
	implementation(project(":bot:commands:impl:bot-commands-impl-wiring"))
	implementation(project(":bot:message-event-handler-mediator:bot-message-event-handler-mediator-wiring"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.moshi)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
