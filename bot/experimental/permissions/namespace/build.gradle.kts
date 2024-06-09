plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	testImplementation(libs.junit.jupiter)
	testRuntimeOnly(libs.junit.platformLauncher)
}

tasks.test {
	useJUnitPlatform()
}
