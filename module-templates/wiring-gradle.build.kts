plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project.TODO.impl)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
