plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	implementation(project(":bot:features:bot-features-all-impl"))
	implementation(project(":bot:commands:bot-commands-impl"))
	implementation(project(":bot:config:supplier:bot-config-supplier-impl-cache"))
	implementation(project(":bot:config:cache:bot-config-cache-impl-memory"))
	implementation(project(":bot:config:source:bot-config-source-impl-file"))
	implementation(project(":bot:config:serialization:bot-config-serialization-impl-json"))
	implementation(project(":snowflake"))
	implementation(project(":bot:logging:bot-logging-wiring"))
	implementation(project(":utils"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)

	implementation(libs.moshi)
}
