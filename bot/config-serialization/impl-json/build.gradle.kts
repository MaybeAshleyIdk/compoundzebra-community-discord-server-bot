plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.configSerialization.api)

	implementation(projects.bot.utils.strings)

	implementation(libs.moshi)
	ksp(libs.moshiKotlinCodegen)
}
