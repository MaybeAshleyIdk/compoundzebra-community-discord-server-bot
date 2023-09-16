plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:features:polls:bot-features-polls-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":snowflake"))
	implementation(project(":bot:logging:bot-logging-public"))

	implementation(libs.jsr305)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
