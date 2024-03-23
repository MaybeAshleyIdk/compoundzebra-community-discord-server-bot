plugins {
	buildSrc.projectType.standalone
	`java-library`
	kotlin("jvm")
}

dependencies {
	implementation(libs.kotlinxCoroutinesCore)
}
