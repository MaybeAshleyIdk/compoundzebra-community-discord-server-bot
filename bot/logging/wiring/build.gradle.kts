plugins {
	`java-library`
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":bot:logging:bot-logging-impl-stderr"))

	implementation(libs.dagger)
	kapt(libs.dagger.compiler)
}
