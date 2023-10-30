plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:emoji-stats:impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
