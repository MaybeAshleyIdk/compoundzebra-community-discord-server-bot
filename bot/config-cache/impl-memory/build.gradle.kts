plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-cache:bot-config-cache-api"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-config-source"))
	implementation(project(":bot:bot-logging"))

	implementation(libs.javax.inject)
}
