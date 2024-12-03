plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.storage.database.api)
	api(projects.bot.shutdown.callbackRegistration)
	api(projects.bot.shutdown.requesting)
	api(projects.bot.logging)

	implementation(projects.bot.utils.coroutinesReadWriteMutex)
	implementation(libs.kotlinxCoroutinesCore)
	implementation(libs.okio)

	runtimeOnly(libs.sqliteJdbc)
}
