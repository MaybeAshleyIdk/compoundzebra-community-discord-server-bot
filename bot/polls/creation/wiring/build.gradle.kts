plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.polls.creation.implManager)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
