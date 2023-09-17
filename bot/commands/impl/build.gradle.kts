plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:models:bot-commands-models-prefix"))
	api(project(":bot:commands:impl:bot-commands-impl-base"))
	api(project(":bot:commands:impl:bot-commands-impl-builtins"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":bot:logging:bot-logging-public"))
	implementation(project(":utils"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
