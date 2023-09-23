plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(project(":bot:scheduledmessages:bot-scheduledmessages-impl"))

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
