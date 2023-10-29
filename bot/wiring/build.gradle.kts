plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:environment-type"))
	implementation(project(":bot:token"))
	implementation(project(":bot:jda-factory"))

	implementation(project(":bot:logging:wiring"))
	implementation(project(":bot:config-supplier:wiring"))
	implementation(project(":bot:config-cache:wiring"))
	implementation(project(":bot:config-source:wiring"))
	implementation(project(":bot:config-serialization:wiring"))
	implementation(project(":bot:emoji-stats:wiring"))
	implementation(project(":bot:polls:wiring"))
	implementation(project(":bot:shutdown-manager:wiring"))
	implementation(project(":bot:shutdown-wait:wiring"))
	implementation(project(":bot:shutdown-request:wiring"))
	implementation(project(":bot:commands:wiring"))
	implementation(project(":bot:message-event-handler-mediator:wiring"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.moshi)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
