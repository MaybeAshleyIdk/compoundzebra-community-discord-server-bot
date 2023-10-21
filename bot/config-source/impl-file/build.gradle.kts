plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-source:bot-config-source-api"))
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-config-serialization"))
	implementation(project(":bot:bot-env"))

	implementation(libs.okio)

	implementation(libs.javax.inject)
}
