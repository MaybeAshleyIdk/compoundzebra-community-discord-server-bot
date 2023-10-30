plugins {
	`java-library`
}

dependencies {
	api(project(":bot:command-message-event-handler:impl:core"))
	api(project(":bot:command-message-event-handler:impl:wiring"))
}
