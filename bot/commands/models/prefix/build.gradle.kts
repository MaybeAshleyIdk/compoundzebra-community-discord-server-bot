plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	implementation(project(":utils"))

	implementation(libs.jsr305)
}
