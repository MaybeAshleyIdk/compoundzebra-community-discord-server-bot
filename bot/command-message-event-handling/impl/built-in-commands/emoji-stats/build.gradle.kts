plugins {
	StandaloneProject
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.command)
	api(projects.bot.configSupplier)
	api(projects.bot.emojiStats)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javax.inject)
}
