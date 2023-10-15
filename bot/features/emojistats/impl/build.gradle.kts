plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:features:emojistats:bot-features-emojistats-public"))
	implementation(project(":bot:logging:bot-logging-public"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
