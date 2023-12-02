plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:storage:database:impl-sqlite"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
