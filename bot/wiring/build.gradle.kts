plugins {
	WiringAssimilationProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(projects.bot.environmentType)
	api(projects.bot.token)
	api(projects.bot.logging)
	api(projects.bot.shutdownCallbackRegistry)

	implementation(projects.bot.jdaFactory)

	implementation(libs.moshi)

	api(libs.dagger)
	ksp(libs.dagger.compiler)
}
