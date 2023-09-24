plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:features:shutdown:wait:bot-features-shutdown-wait-impl-manager"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
