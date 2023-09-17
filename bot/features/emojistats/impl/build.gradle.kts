plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:features:emojistats:bot-features-emojistats-public"))
	api(project(":bot:logging:bot-logging-public"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
