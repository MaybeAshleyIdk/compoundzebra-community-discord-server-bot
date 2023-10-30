plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config-supplier:impl-cache"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
