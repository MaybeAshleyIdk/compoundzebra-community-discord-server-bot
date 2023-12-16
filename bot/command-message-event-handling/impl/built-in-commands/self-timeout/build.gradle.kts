plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.command)
	api(projects.bot.selfTimeout)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javax.inject)
}
