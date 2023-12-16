plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.configSupplier)

	implementation(libs.javax.inject)
}
