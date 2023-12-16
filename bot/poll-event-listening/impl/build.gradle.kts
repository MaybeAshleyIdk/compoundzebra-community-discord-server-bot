plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollEventListening.api)
	api(projects.bot.pollComponentProtocol)
	api(projects.bot.pollModification)
	api(projects.bot.configSupplier)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
