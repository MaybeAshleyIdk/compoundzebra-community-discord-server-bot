plugins {
	InternalWiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:built-in-commands"))
	api(project(":bot:command-message-event-handling:impl:predefined-response-command"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
