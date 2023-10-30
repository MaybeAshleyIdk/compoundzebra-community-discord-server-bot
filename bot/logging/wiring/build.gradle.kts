plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:logging:impl-stderr"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
