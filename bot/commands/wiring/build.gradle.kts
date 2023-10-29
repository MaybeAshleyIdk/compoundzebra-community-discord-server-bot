plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:command-name"))
	api(project(":bot:commands:base"))
	api(project(":bot:commands:echo-command"))
	api(project(":bot:commands:message-parser"))
	api(project(":bot:commands:builtins:all-wiring"))
	implementation(project(":bot:config"))
	implementation(project(":bot:config-supplier"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
