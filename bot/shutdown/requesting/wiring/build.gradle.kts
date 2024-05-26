plugins {
	buildSrc.projectStructure.`service-wiring`

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdown.requesting.implManager)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
