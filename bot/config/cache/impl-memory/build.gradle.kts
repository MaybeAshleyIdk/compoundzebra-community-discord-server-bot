plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:config:cache:bot-config-cache-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:source:bot-config-source-public"))
	implementation(project(":bot:bot-logging"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}
