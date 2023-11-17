plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:private-message-event-handling:api"))

	implementation(project(":bot:logging"))
	implementation(project(":bot:utils"))

	implementation(libs.javax.inject)
}
