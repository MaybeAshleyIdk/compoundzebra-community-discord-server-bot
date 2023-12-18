plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.shutdown.management.impl)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
