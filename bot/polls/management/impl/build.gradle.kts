plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.management.api)
	api(projects.bot.logging)

	implementation(projects.bot.snowflakeGenerator)
	implementation(projects.bot.utils.strings)
	implementation(projects.bot.utils.coroutines)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.javaxInject)
}
