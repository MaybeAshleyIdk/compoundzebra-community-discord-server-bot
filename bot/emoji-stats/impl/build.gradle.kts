plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.emojiStats.api)
	api(projects.bot.logging)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
