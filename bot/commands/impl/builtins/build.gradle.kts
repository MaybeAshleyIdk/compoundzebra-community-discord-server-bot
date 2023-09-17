plugins {
	`java-library`
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-config"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-emojistats"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-magic8ball"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-polls"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-shutdown"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
