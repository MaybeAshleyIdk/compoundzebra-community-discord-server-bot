plugins {
	`java-library`
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":bot:config:source:bot-config-source-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:serialization:bot-config-serialization-public"))

	implementation(libs.okio)

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
