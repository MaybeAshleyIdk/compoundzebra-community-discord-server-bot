plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.logging.api)

	implementation(libs.javax.inject)
}
