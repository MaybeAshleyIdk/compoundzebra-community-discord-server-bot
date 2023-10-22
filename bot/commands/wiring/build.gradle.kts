plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	api(project(":bot:commands:bot-commands-base"))
	api(project(":bot:commands:bot-commands-echo-command"))
	api(project(":bot:commands:builtins:bot-commands-builtins-all-wiring"))
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-config-supplier"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
