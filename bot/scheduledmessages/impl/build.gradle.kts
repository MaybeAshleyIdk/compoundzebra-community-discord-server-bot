plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:scheduledmessages:bot-scheduledmessages-public"))
	implementation(project(":bot:config:bot-config-models"))
	implementation(project(":bot:config:supplier:bot-config-supplier-public"))

	implementation(libs.jda) {
		exclude(module = "opus-java")
	}

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
