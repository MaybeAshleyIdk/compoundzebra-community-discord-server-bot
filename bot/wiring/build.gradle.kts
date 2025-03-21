plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

dependencies {
	api(`jda-without-opusJava`)
	api(projects.bot.buildType)
	api(projects.bot.token)
	api(projects.bot.logging)
	api(projects.bot.shutdown.callbackRegistration)

	api(projects.bot.utils.di)

	implementation(projects.bot.jdaFactory)

	implementation(libs.moshi)

	implementation(projects.bot.commands.messageEventHandling.impl)
	implementation(projects.bot.conditionalMessageEventHandling.impl)
	implementation(projects.bot.configCache.implMemory)
	implementation(projects.bot.configSerialization.implJson)
	implementation(projects.bot.configSource.implFile)
	implementation(projects.bot.configSupplier.implCache)
	implementation(projects.bot.emojiStats.impl)
	implementation(projects.bot.eventListening.impl)
	implementation(projects.bot.logging.implStderr)
	implementation(projects.bot.messageEventHandlerMediation.impl)
	implementation(projects.bot.polls.wiring)
	implementation(projects.bot.privateMessageEventHandling.impl)
	implementation(projects.bot.selfTimeout.impl)
	implementation(projects.bot.shutdown.wiring)
	implementation(projects.bot.storage.wiring)
}
