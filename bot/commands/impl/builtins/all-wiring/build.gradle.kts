plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-coinflip"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-config"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-dev"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-magic8ball"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-polls"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-rng"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-shutdown"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
