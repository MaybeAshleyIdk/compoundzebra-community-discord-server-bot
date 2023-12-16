plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.polls.management.api)
	api(projects.bot.logging)

	implementation(projects.bot.snowflakeGenerator)
	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutines)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
