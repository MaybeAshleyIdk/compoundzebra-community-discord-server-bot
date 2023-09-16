plugins {
	kotlin("jvm")
	`java-library`
}

dependencies {
	api(project(":bot:logging:bot-logging-public"))

	implementation("com.google.code.findbugs:jsr305:3.0.2")

	implementation("javax.inject:javax.inject:1")
}
