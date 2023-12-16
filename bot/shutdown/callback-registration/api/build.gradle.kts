plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdown.callbacks)

	implementation(libs.kotlinx.coroutines.core)
}
