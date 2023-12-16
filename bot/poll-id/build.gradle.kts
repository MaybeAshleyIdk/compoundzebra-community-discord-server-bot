plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.snowflake)
}
