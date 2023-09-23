plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:config:cache:bot-config-cache-impl-memory"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
