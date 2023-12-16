plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.shutdownEventHandling.api)
	api(projects.bot.shutdownManager)

	implementation(libs.javax.inject)
}
