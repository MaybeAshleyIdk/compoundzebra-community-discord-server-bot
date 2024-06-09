plugins {
	buildSrc.projectStructure.`service-interface`

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(`jda-without-opusJava`)
}
