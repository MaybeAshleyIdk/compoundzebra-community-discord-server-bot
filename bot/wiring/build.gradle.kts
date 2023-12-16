plugins {
	`java-library`
	kotlin("jvm")
	alias(libs.plugins.ksp)
}

dependencies {
	api(libs.jda) {
		exclude(module = "opus-java")
	}
	api(projects.bot.environmentType)
	api(projects.bot.token)
	api(projects.bot.logging)
	api(projects.bot.shutdown.callbackRegistration)

	implementation(projects.bot.jdaFactory)

	implementation(libs.moshi)

	api(libs.dagger)
	ksp(libs.dagger.compiler)

	implementation(projects.bot.commands.messageEventHandling.wiring)
	implementation(projects.bot.conditionalMessageEventHandling.wiring)
	implementation(projects.bot.configCache.wiring)
	implementation(projects.bot.configSerialization.wiring)
	implementation(projects.bot.configSource.wiring)
	implementation(projects.bot.configSupplier.wiring)
	implementation(projects.bot.emojiStats.wiring)
	implementation(projects.bot.eventListening.wiring)
	implementation(projects.bot.logging.wiring)
	implementation(projects.bot.messageEventHandlerMediation.wiring)
	implementation(projects.bot.pollCreation.wiring)
	implementation(projects.bot.pollEventListening.wiring)
	implementation(projects.bot.pollHolding.wiring)
	implementation(projects.bot.pollManagement.wiring)
	implementation(projects.bot.pollModification.wiring)
	implementation(projects.bot.privateMessageEventHandling.wiring)
	implementation(projects.bot.selfTimeout.wiring)
	implementation(projects.bot.shutdown.wiring)
}
