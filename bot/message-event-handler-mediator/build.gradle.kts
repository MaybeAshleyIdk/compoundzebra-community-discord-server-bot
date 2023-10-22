plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:commands:bot-commands-impl"))
	implementation(project(":bot:bot-conditional-messages"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
