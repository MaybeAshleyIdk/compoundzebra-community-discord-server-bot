plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.management.api)
	api(projects.bot.logging)

	implementation(projects.bot.utils.coroutinesAtomic)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
