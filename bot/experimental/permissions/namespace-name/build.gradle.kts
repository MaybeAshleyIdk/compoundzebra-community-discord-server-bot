plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(projects.bot.utils)

	testImplementation(libs.junit.jupiter)
	testRuntimeOnly(libs.junit.platformLauncher)
}
