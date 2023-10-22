plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config-serialization:api"))
	implementation(project(":bot:config"))
	implementation(project(":bot:command-name"))
	implementation(project(":bot:command-prefix"))

	implementation(libs.okio)

	implementation(libs.moshi)
	ksp(libs.moshi.kotlin.codegen)

	implementation(libs.javax.inject)
}
