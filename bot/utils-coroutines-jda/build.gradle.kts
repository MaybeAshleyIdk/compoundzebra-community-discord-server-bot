plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(`jda-without-opusJava`)

	implementation(libs.kotlinxCoroutinesCore)
}
