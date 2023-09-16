plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	api(project(":bot:logging:bot-logging-public"))

	implementation(libs.jsr305)

	implementation(libs.javax.inject)
}
