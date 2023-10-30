plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:shutdown-manager:impl-semaphore"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
