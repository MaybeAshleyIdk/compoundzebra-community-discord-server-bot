plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config-source:impl-file"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
