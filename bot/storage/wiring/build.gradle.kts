plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.storage.database.wiring)

	api(libs.dagger)
	ksp(libs.daggerCompiler)
}
