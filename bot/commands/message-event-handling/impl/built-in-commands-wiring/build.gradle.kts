plugins {
	buildSrc.projectStructure.standalone

	`java-library`
	kotlin("jvm")

	alias(libs.plugins.ksp)
}

val builtInCommandsProject: Project = projects.bot.commands.messageEventHandling.impl.builtInCommands
	.dependencyProject

evaluationDependsOn(builtInCommandsProject.path)

for (builtInCommandProject: Project in builtInCommandsProject.childProjects.values) {
	evaluationDependsOn(builtInCommandProject.path)

	dependencies {
		api(project(builtInCommandProject.path))
	}
}

dependencies {
	implementation(libs.dagger)
	ksp(libs.daggerCompiler)
}
