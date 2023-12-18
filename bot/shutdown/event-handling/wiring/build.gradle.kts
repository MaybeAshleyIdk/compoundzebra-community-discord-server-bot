plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdown.eventHandling.implManager)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
