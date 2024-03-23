plugins {
	buildSrc.projectType.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.kotlinxCoroutinesCore)
}
