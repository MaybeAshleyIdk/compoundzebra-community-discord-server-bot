plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.selfTimeout.api)
	api(projects.bot.logging)

	implementation(projects.bot.utils.strings)
	implementation(projects.bot.utils.coroutines)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
