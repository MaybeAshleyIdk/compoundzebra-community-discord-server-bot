plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:commands:builtins:coinflip"))
	api(project(":bot:commands:builtins:config"))
	api(project(":bot:commands:builtins:dev"))
	api(project(":bot:commands:builtins:magic8ball"))
	api(project(":bot:commands:builtins:polls"))
	api(project(":bot:commands:builtins:rng"))
	api(project(":bot:commands:builtins:shutdown"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
