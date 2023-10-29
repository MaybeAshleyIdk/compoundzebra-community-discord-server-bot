plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:message-event-handler-mediator:impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
