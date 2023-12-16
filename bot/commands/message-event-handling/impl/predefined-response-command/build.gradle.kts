plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.command)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.kotlinx.coroutines.core)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
