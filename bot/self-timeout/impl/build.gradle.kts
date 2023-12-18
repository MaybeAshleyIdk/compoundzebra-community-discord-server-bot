plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.selfTimeout.api)
	api(projects.bot.logging)

	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutines)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
