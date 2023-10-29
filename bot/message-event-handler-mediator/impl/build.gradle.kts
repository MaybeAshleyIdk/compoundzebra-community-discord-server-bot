plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:message-event-handler-mediator:api"))

	api(project(":bot:commands:event-handler"))
	api(project(":bot:conditional-messages"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
