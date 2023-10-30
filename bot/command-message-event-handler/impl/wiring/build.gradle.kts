plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:command-message-event-handler:impl:built-in-commands"))
	api(project(":bot:command-message-event-handler:impl:predefined-response-command"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
