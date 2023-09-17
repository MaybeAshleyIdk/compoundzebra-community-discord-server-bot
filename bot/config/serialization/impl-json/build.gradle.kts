plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:config:serialization:bot-config-serialization-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:models:bot-commands-models-prefix"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)

	implementation(libs.okio)

	implementation(libs.moshi)
	kapt(libs.moshi.kotlin.codegen)
}
