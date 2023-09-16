plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:config:cache:bot-config-cache-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:source:bot-config-source-public"))
	implementation(project(":bot:logging:bot-logging-public"))

	implementation(libs.jsr305)

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
