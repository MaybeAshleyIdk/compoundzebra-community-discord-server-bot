plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:logging:bot-logging-public"))

	implementation(libs.javax.inject)
}
