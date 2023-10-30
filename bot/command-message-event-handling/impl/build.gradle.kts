plugins {
	`java-library`
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:core"))
	api(project(":bot:command-message-event-handling:impl:wiring"))
}
