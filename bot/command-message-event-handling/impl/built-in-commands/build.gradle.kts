plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.coinFlip)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.config)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.dev)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.emojiStats)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.magic8ball)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.polls)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.rng)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.selfTimeout)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.shutdown)
	api(projects.bot.commandMessageEventHandling.impl.builtInCommands.sourceCode)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
