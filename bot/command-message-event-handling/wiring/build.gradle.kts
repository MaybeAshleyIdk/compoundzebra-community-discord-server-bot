plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl)

	api(projects.bot.commandMessageEventHandling.impl.wiring)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
