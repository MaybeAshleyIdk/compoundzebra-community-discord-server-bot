plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:cache:bot-config-cache-public"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
