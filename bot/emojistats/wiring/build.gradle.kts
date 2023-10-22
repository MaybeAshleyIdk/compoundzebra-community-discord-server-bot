plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:emojistats:impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
