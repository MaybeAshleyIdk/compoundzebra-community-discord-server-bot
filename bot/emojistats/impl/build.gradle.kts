plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:emojistats:bot-emojistats-api"))
	implementation(project(":bot:bot-logging"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
