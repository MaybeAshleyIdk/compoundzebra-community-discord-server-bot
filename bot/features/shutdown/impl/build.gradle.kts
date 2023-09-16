plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:features:shutdown:bot-features-shutdown-public"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
