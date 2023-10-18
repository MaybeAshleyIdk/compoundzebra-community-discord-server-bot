plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config:serialization:bot-config-serialization-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-command-name"))
	implementation(project(":bot:bot-command-prefix"))

	implementation(libs.okio)

	implementation(libs.moshi)
	ksp(libs.moshi.kotlin.codegen)

	implementation(libs.javax.inject)
}
