plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(TODO()))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
