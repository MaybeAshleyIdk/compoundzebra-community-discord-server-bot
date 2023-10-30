plugins {
	WiringAssimilationProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(project(":bot:environment-type"))
	api(project(":bot:token"))
	api(project(":bot:logging"))
	api(project(":bot:shutdown-wait"))

	implementation(project(":bot:jda-factory"))

	implementation(libs.moshi)

	api(libs.dagger)
	ksp(libs.dagger.compiler)
}
