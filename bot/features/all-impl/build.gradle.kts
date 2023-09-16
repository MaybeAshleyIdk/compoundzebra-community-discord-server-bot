plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:features:emojistats:bot-features-emojistats-impl"))
	api(project(":bot:features:shutdown:bot-features-shutdown-impl"))
	api(project(":bot:features:polls:bot-features-polls-impl"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
