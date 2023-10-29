plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:environment-type"))
	implementation(project(":bot:token"))
	implementation(project(":bot:wiring"))

	implementation(project(":bot:logging"))
	implementation(project(":bot:shutdown-wait"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
}
