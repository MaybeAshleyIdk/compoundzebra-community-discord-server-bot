plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config-supplier:bot-config-supplier-impl-cache"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
