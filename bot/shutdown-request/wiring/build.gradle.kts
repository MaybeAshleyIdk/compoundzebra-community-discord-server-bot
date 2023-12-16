plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdownRequest.implManager)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
