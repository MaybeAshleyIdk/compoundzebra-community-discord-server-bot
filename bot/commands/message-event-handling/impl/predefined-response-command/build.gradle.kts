plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.command)

	implementation(projects.bot.utils.coroutinesJda)

	implementation(libs.kotlinxCoroutinesCore)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
