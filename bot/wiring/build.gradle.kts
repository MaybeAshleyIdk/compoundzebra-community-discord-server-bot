plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:models:bot-models-env"))
	implementation(project(":bot:models:bot-models-token"))

	// eventually, the rest of these impl modules should be replaced with wiring ones
	implementation(project(":bot:logging:bot-logging-wiring"))
	implementation(project(":bot:config:bot-config-wiring"))
	implementation(project(":bot:features:bot-features-all-wiring"))
	implementation(project(":bot:commands:bot-commands-impl"))
	implementation(project(":bot:conditionalmessages:bot-conditionalmessages-wiring"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.moshi)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}