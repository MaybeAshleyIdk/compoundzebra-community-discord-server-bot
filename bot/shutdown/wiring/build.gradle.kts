plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdown.management.wiring)
	api(projects.bot.shutdown.eventHandling.wiring)
	api(projects.bot.shutdown.callbackRegistration.wiring)
	api(projects.bot.shutdown.requesting.wiring)

	api(libs.dagger)
	ksp(libs.dagger.compiler)
}
