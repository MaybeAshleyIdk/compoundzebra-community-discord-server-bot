plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:features:shutdown:bot-features-shutdown-impl"))
	api(project(":bot:features:polls:bot-features-polls-impl"))

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}
