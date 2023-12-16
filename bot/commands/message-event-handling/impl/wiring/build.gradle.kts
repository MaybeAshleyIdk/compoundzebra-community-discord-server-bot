plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands)
	api(projects.bot.commands.messageEventHandling.impl.predefinedResponseCommand)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
