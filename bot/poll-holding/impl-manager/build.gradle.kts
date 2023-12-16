plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.pollHolding.api)
	api(projects.bot.pollManagement)

	implementation(libs.javax.inject)
}
