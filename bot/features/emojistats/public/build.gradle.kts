plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
