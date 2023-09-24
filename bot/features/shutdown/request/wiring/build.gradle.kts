plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:features:shutdown:request:bot-features-shutdown-request-impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
