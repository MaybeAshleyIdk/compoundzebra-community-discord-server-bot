plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:command-message-event-handler:impl:built-in-commands:coin-flip"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:config"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:dev"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:emoji-stats"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:magic8ball"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:polls"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:rng"))
	api(project(":bot:command-message-event-handler:impl:built-in-commands:shutdown"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
