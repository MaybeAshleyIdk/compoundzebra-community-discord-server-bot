plugins {
	GroupProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:storage:database:wiring"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
