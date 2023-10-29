plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:commands:base"))
	api(project(":bot:config-supplier"))
	api(project(":bot:shutdown-request"))

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
