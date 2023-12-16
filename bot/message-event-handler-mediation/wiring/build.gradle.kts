plugins {
	WiringProject
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.messageEventHandlerMediation.impl)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
