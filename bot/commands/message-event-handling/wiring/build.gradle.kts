plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl)

	api(projects.bot.commands.messageEventHandling.impl.wiring)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
