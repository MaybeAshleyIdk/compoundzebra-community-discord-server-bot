plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:polls:api"))
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(project(":bot:logging"))
	api(project(":bot:config-supplier"))

	implementation(project(":bot:snowflake-generator"))
	implementation(project(":bot:utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
