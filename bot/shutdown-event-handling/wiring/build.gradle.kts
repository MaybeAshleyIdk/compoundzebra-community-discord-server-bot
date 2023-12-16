plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdownEventHandling.implManager)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
