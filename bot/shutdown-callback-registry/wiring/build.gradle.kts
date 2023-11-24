plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:shutdown-callback-registry:impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
