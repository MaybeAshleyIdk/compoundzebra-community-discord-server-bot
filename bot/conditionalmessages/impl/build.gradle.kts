plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.javax.inject)
}
