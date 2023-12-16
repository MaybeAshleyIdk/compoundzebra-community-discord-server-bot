plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.pollManagement.impl)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
