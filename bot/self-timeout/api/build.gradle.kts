plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
}
