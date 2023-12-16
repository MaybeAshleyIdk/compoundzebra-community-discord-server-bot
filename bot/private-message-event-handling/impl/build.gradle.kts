plugins {
	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.privateMessageEventHandling.api)

	implementation(projects.bot.logging)
	implementation(projects.bot.utils)

	implementation(libs.javax.inject)
}
