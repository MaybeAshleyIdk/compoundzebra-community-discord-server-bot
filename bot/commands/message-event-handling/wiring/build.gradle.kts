plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
