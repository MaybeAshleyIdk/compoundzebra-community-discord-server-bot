plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(project(":bot:event-listening"))

	implementation(libs.javax.inject)
}
