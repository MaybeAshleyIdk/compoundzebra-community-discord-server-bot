plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.coinFlip)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.config)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.dev)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.emojiStats)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.magic8ball)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.polls)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.rng)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.selfTimeout)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.shutdown)
	api(projects.bot.commands.messageEventHandling.impl.builtInCommands.sourceCode)

	implementation(libs.dagger)
	ksp(libs.dagger.compiler)
}
