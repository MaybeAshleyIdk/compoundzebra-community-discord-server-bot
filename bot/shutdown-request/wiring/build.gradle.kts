plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:shutdown-request:impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
