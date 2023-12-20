plugins {
	buildSrc.projectType.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(projects.bot.utils)
}
