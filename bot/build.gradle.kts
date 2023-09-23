plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:features:bot-features-all-impl"))
	implementation(project(":bot:commands:bot-commands-impl"))
	implementation(project(":bot:config:supplier:bot-config-supplier-impl-cache"))
	implementation(project(":bot:config:cache:bot-config-cache-impl-memory"))
	implementation(project(":bot:config:source:bot-config-source-impl-file"))
	implementation(project(":bot:config:serialization:bot-config-serialization-impl-json"))
	implementation(project(":bot:bot-snowflake"))
	implementation(project(":bot:logging:bot-logging-wiring"))
	implementation(project(":bot:scheduledmessages:bot-scheduledmessages-wiring"))
	implementation(project(":utils"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.moshi)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
