plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:features:polls:bot-features-polls-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":bot:bot-snowflake"))
	implementation(project(":bot:logging:bot-logging-public"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
