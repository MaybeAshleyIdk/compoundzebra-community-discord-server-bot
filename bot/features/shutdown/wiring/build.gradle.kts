plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:features:shutdown:manager:bot-features-shutdown-manager-wiring"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
