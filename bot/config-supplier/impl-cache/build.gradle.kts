plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:config-supplier:bot-config-supplier-api"))
	implementation(project(":bot:bot-config"))
	implementation(project(":bot:bot-config-cache"))

	implementation(libs.javax.inject)
}