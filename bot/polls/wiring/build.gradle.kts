plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.polls.creation.implManager)
	api(projects.bot.polls.eventHandling.wiring)
	api(projects.bot.polls.holding.implManager)
	api(projects.bot.polls.management.impl)
	api(projects.bot.polls.modification.wiring)

	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
