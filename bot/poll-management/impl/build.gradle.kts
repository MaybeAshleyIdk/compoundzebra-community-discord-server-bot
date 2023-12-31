plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollManagement.api)
	api(projects.bot.logging)

	implementation(projects.bot.snowflakeGenerator)
	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutines)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
