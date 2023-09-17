plugins {
	`java-library`
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	implementation(project(":bot:commands:models:bot-commands-models-name"))
	implementation(project(":bot:commands:impl:bot-commands-impl-base"))
	implementation(project(":bot:features:polls:bot-features-polls-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))
	implementation(project(":utils"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
