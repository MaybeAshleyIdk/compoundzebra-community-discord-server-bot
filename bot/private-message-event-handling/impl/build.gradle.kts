plugins {
	buildSrc.projectStructure.`service-implementation`.standalone

	`java-library`
	kotlin("jvm")
}

dependencies {
	api(projects.bot.privateMessageEventHandling.api)

	implementation(projects.bot.logging)
	implementation(projects.bot.utils)

	implementation(libs.javaxInject)
}
