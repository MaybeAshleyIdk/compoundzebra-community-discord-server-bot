plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:commands:builtins:all-wiring"))
	api(project(":bot:commands:echo-command"))
	api(project(":bot:commands:message-parser"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
