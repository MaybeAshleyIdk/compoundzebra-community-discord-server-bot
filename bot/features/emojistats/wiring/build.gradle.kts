plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:features:emojistats:bot-features-emojistats-impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
