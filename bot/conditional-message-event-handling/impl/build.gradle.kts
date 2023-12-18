plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.conditionalMessageEventHandling.api)
	api(projects.bot.configSupplier)
	api(projects.bot.logging)

	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
