plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
