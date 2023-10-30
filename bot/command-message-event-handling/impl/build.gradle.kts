plugins {
	ImplProject
	`java-library`
}

dependencies {
	api(project(":bot:command-message-event-handling:impl:core"))
}
