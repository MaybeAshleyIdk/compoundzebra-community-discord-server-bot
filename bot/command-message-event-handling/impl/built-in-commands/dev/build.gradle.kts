plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.command)

	implementation(projects.bot.utilsCoroutinesJda)

	implementation(libs.javax.inject)
}
