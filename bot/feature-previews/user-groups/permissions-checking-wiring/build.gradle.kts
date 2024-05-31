plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.featurePreviews.userGroups.permissionsCheckingImpl)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
