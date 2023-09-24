plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:features:shutdown:bot-features-shutdown-impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
