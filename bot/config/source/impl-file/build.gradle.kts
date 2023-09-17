plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config:source:bot-config-source-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:serialization:bot-config-serialization-public"))

	implementation(libs.okio)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
