plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.polls.creation.implManager)
	api(projects.bot.polls.eventHandling.impl)
	api(projects.bot.polls.holding.implManager)
	api(projects.bot.polls.management.impl)
	api(projects.bot.polls.modification.implManager)

	api(projects.bot.utils.di)
}
