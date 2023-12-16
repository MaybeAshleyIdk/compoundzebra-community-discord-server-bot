plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(projects.bot.utils)
}
