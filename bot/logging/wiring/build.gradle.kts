plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:logging:bot-logging-impl-stderr"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
