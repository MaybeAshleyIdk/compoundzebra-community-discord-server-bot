plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:logging:bot-logging-api"))

	implementation(libs.javax.inject)
}
