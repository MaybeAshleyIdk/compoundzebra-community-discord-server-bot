plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(project(":bot:bot-command-name"))
	implementation(project(":bot:commands:impl:bot-commands-impl-base"))
	implementation(project(":bot:features:polls:bot-features-polls-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:bot-config-supplier"))
	implementation(project(":bot:bot-utils"))
	implementation(project(":bot:bot-utils-coroutines-jda"))

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
