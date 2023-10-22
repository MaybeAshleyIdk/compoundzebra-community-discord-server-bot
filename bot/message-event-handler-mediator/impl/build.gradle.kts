plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:commands:event-handler"))
	api(project(":bot:conditional-messages"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
