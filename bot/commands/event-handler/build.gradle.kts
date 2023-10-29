plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:commands:message-parser"))
	api(project(":bot:commands:base"))
	api(project(":bot:logging"))
	api(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
