plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:command-name"))
	implementation(project(":bot:command-prefix"))
	implementation(project(":bot:commands:base"))
	implementation(project(":bot:config"))
	implementation(project(":bot:config-supplier"))
	implementation(project(":bot:logging"))
	implementation(project(":bot:utils"))
	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
