plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:commands:impl:bot-commands-impl-event-handler"))
	implementation(project(":bot:bot-conditional-messages"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
