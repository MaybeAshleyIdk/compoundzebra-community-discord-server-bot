plugins {
	buildSrc.projectType.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.snowflake)
}
