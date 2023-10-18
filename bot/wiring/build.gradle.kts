plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-env"))
	implementation(project(":bot:bot-token"))

	// eventually, the rest of these impl modules should be replaced with wiring ones
	implementation(project(":bot:logging:bot-logging-wiring"))
	implementation(project(":bot:config-supplier:bot-config-supplier-wiring"))
	implementation(project(":bot:config:bot-config-wiring"))
	implementation(project(":bot:features:bot-features-all-wiring"))
	implementation(project(":bot:commands:bot-commands-impl"))
	implementation(project(":bot:eventlistenermediator:bot-eventlistenermediator-wiring"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.moshi)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
