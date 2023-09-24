plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config:source:bot-config-source-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:serialization:bot-config-serialization-public"))
	implementation(project(":bot:models:bot-models-env"))

	implementation(libs.okio)

	implementation(libs.javax.inject)
}
