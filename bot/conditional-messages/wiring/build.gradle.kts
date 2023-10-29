plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:conditional-messages:impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
