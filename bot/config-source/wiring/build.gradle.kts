plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.configSource.implFile)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
