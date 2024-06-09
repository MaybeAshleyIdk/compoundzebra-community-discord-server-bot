plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(projects.bot.utils.coroutinesReadWriteMutex)
	implementation(libs.kotlinxCoroutinesCore)
}
