plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(libs.jsr305)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}
}
