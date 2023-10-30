plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:poll-holding:impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
