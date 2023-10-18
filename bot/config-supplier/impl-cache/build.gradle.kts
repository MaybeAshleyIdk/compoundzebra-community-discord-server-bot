plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-supplier:bot-config-supplier-api"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:cache:bot-config-cache-public"))

	implementation(libs.javax.inject)
}
