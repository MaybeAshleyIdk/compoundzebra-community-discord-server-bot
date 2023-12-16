plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.pollCreation.implManager)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
