plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdown.management.impl)
	api(projects.bot.shutdown.eventHandling.implManager)
	api(projects.bot.shutdown.callbackRegistration.implManager)
	api(projects.bot.shutdown.requesting.implManager)

	api(projects.bot.utils.di)
}
