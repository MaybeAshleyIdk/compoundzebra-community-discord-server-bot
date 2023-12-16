plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands)
	api(projects.bot.commandMessageEventHandling.impl.predefinedResponseCommand)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
