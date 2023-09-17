plugins {
	`java-library`
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":bot:features:shutdown:bot-features-shutdown-public"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
