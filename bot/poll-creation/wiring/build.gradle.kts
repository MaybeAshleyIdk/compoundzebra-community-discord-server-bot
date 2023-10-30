plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:poll-creation:impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
