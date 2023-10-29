plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:commands:base"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
