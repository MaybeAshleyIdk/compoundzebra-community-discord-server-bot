plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	implementation(project(":bot:bot-command-prefix"))
	api(project(":bot:commands:impl:bot-commands-impl-base"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-all-wiring"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-config-supplier"))
	implementation(project(":bot:bot-logging"))
	implementation(project(":bot:bot-utils"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
