plugins {
	`java-library`
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":bot:config:serialization:bot-config-serialization-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:models:bot-commands-models-prefix"))

	implementation(libs.okio)

	implementation(libs.moshi)
	kapt(libs.moshi.kotlin.codegen)

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
