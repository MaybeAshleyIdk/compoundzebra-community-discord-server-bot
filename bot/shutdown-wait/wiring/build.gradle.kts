plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:shutdown-wait:bot-shutdown-wait-impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
