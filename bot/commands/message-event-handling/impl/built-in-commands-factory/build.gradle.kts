plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")
}

val builtInCommandProjectPaths: List<String> = parent!!
	.project("built-in-commands")
	.childProjects.values
	.map(Project::getPath)

for (builtInCommandProjectPath: String in builtInCommandProjectPaths) {
	evaluationDependsOn(builtInCommandProjectPath)
}

dependencies {
	api(projects.bot.configSupplier)
	// api(projects.bot.emojiStats)
	api(projects.bot.polls.creation)
	api(projects.bot.polls.holding)
	api(projects.bot.selfTimeout)
	api(projects.bot.shutdown.requesting)
	api(projects.bot.environmentType)
	api(projects.bot.logging)

	api(projects.bot.commands.messageEventHandling.impl.command)

	for (builtInCommandProjectDependency: ProjectDependency in builtInCommandProjectPaths.map(this::project)) {
		implementation(builtInCommandProjectDependency)
	}
}
