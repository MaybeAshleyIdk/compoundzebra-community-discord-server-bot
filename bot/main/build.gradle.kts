plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.environmentType)
	api(projects.bot.token)

	implementation(projects.bot.wiring)

	implementation(libs.kotlinx.coroutines.core)
}
