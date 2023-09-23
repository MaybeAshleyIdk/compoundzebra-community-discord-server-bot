plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config:serialization:bot-config-serialization-wiring"))
	api(project(":bot:config:source:bot-config-source-wiring"))
	api(project(":bot:config:cache:bot-config-cache-wiring"))
	api(project(":bot:config:supplier:bot-config-supplier-wiring"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
