plugins {
	ImplProject
	`java-library`
}

dependencies {
	api(projects.bot.commandMessageEventHandling.impl.core)
}
