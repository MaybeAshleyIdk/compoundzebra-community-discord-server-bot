plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.manager.api)
	api(projects.bot.logging)

	implementation(projects.bot.utilsCoroutines)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
