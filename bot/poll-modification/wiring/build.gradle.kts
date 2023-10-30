plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:poll-modification:impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
