plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.privateMessageEventHandling.impl)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
