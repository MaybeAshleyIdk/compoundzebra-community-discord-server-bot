plugins {
	kotlin("jvm")
	kotlin("kapt")
	`java-library`
}

dependencies {
	api(project(":bot:logging:bot-logging-impl-stderr"))

	implementation(libs.jsr305)

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}