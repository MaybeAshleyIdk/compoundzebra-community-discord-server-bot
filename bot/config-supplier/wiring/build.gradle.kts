plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.configSupplier.implCache)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
