plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:storage:guild-member:impl-db"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
