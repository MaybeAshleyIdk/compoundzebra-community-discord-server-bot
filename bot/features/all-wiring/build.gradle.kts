plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:features:polls:bot-features-polls-wiring"))
	api(project(":bot:features:shutdown:bot-features-shutdown-wiring"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
