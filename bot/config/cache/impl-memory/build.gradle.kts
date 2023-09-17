plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config:cache:bot-config-cache-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:source:bot-config-source-public"))
	implementation(project(":bot:logging:bot-logging-public"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
