plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	api(project(":bot:commands:impl:bot-commands-impl-base"))
	api(project(":bot:commands:impl:bot-commands-impl-echo-command"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-all-wiring"))
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-config-supplier"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
