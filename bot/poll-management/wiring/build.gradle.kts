plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:poll-management:impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
