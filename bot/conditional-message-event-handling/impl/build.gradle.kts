plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.conditionalMessageEventHandling.api)
	api(projects.bot.configSupplier)
	api(projects.bot.logging)

	implementation(projects.bot.utils.strings)
	implementation(projects.bot.utils.coroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
