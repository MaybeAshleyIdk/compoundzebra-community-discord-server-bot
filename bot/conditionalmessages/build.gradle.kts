plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-config-supplier"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
