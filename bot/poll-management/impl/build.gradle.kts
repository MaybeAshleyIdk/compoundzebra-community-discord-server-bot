plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(project(":bot:poll-management:api"))
	api(project(":bot:logging"))

	implementation(project(":bot:snowflake-generator"))
	implementation(project(":bot:utils"))

	implementation(libs.javax.inject)
}
