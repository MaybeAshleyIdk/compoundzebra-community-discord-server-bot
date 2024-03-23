plugins {
	buildSrc.projectType.`service-wiring`
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.configSource.implFile)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
