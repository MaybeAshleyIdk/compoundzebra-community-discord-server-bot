plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.storage.database.implSqlite)

	api(libs.dagger)
	ksp(libs.daggerCompiler)
}
