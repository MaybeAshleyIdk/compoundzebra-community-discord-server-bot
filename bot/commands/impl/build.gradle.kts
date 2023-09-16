plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:models:bot-commands-models-prefix"))
	api(project(":bot:commands:impl:bot-commands-impl-base"))
	api(project(":bot:commands:impl:bot-commands-impl-builtins"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":bot:logging:bot-logging-public"))
	implementation(project(":utils"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("net.dv8tion:JDA:5.0.0-beta.13") {
		exclude(module = "opus-java")
	}

	implementation("com.google.dagger:dagger:2.47")
	kapt("com.google.dagger:dagger-compiler:2.47")
}
