plugins {
	buildSrc.projectStructure.`service-wiring`

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(TODO("Service-wiring project must have an API dependency on the corresponding service-implementation sibling project"))

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
