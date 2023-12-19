plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(projects.bot.eventListening)

	implementation(libs.kotlinxCoroutinesCore)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javaxInject)
}
