plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:emoji-stats:api"))
	implementation(project(":bot:logging"))
	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
