plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:shutdown-manager:bot-shutdown-manager-impl-semaphore"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}