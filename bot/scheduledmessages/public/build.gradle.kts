plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
