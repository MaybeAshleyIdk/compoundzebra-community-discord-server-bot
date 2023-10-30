plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:conditional-message-event-handling:impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
