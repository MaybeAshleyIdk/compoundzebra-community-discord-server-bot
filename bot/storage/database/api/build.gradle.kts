plugins {
	buildSrc.projectStructure.`service-interface`

	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(libs.kotlinxCoroutinesCore)
}
