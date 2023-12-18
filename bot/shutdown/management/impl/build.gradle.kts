plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.management.api)
	api(projects.bot.logging)

	implementation(projects.bot.utilsCoroutines)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
