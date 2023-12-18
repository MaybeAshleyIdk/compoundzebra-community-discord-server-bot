plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.polls.sending.impl)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
