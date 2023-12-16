plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.pollHolding.implManager)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
