plugins {
	ApiImplWiringProject
	`java-library`
}

dependencies {
	api(projects.bot.configSupplier.api)
}
