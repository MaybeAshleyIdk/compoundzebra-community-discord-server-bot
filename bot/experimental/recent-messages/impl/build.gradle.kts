plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.experimental.recentMessages.api)

	implementation(projects.bot.logging)
	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
