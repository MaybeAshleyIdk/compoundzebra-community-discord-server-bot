plugins {
	GroupProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
