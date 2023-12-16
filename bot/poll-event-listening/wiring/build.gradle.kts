plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.pollEventListening.impl)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}