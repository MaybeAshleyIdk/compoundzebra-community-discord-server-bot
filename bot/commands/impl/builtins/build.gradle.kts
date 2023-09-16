plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-config"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-emojistats"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-magic8ball"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-polls"))
	api(project(":bot:commands:impl:builtins:bot-commands-impl-builtins-shutdown"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}
