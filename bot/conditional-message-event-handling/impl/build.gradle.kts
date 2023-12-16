plugins {
	ImplProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.conditionalMessageEventHandling.api)
	api(projects.bot.configSupplier)
	api(projects.bot.logging)

	implementation(projects.bot.utils)
	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.javax.inject)
}
