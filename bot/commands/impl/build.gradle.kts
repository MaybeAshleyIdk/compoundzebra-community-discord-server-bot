plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:models:bot-commands-models-prefix"))
	api(project(":bot:commands:impl:bot-commands-impl-base"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-all-wiring"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":bot:logging:bot-logging-public"))
	implementation(project(":bot:bot-utils"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
