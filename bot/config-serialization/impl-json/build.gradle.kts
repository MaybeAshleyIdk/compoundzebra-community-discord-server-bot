plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config-serialization:api"))

	implementation(libs.moshi)
	ksp(libs.moshi.kotlin.codegen)

	implementation(libs.javax.inject)
}
