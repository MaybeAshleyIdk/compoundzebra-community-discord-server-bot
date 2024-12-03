plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.storage.database.implSqlite)

	api(projects.bot.utils.di)
}
