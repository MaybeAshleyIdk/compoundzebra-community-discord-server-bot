plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.configSerialization.api)

	implementation(libs.moshi)
	ksp(libs.moshiKotlinCodegen)

	implementation(libs.javaxInject)
}
