plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config:serialization:bot-config-serialization-impl-json"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
