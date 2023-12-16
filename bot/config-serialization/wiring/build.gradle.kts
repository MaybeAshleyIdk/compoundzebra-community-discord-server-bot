plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.configSerialization.implJson)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
