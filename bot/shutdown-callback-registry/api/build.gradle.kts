plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdownCallbacks)

	implementation(libs.kotlinx.coroutines.core)
}
