package io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.settingsinclude.internal

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.includedsl.ProjectInclusionInfoDetails
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectname.ProjectName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.projectpath.ProjectPath
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.build.projectsstructuresystem.serviceimplementationname.ServiceImplementationName
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings

private val SERVICE_INTERFACE_PROJECT_NAME: ProjectName = ProjectName.ofString("api")!!

internal fun Settings.includeRecursivelyAtTopLevel(topLevelProjects: Map<ProjectName, ProjectInclusionInfoDetails>) {
	this.includeRecursively(parentProjectPath = ProjectPath.Root, projects = topLevelProjects)
}

private fun Settings.includeRecursively(
	parentProjectPath: ProjectPath,
	projects: Map<ProjectName, ProjectInclusionInfoDetails>,
) {
	for ((projectName: ProjectName, projectInclusionInfoDetails: ProjectInclusionInfoDetails) in projects) {
		this.includeRecursively(parentProjectPath, projectName, projectInclusionInfoDetails)
	}
}

private fun Settings.includeRecursively(
	parentProjectPath: ProjectPath,
	projectName: ProjectName,
	projectInclusionInfoDetails: ProjectInclusionInfoDetails,
) {
	val projectPath: ProjectPath = (parentProjectPath + projectName)

	this.includeRecursively(projectPath, projectInclusionInfoDetails)

	this.include(projectPath, projectInclusionInfoDetails.directoryName)
}

private fun Settings.includeRecursively(
	projectPath: ProjectPath,
	projectInclusionInfoDetails: ProjectInclusionInfoDetails,
) {
	when (projectInclusionInfoDetails) {
		is ProjectInclusionInfoDetails.Standalone -> Unit

		is ProjectInclusionInfoDetails.Namespace -> {
			this.includeRecursively(parentProjectPath = projectPath, projectInclusionInfoDetails.childProjects)
		}

		is ProjectInclusionInfoDetails.Composite -> {
			this.includeRecursively(parentProjectPath = projectPath, projectInclusionInfoDetails.childProjects)
		}

		is ProjectInclusionInfoDetails.Service -> {
			this.include(projectPath + SERVICE_INTERFACE_PROJECT_NAME)

			val implementationProjects: Map<ProjectName, ProjectInclusionInfoDetails> =
				projectInclusionInfoDetails.implementationProjects
					.mapKeys { (implName: ServiceImplementationName, _: ProjectInclusionInfoDetails) ->
						implName.toFullProjectName()
					}
			this.includeRecursively(parentProjectPath = projectPath, implementationProjects)
		}
	}
}

private fun Settings.include(projectPath: ProjectPath, directoryName: String?) {
	this.include(projectPath)

	if (directoryName != null) {
		val projectDescriptor: ProjectDescriptor = this.project(projectPath.toString())
		projectDescriptor.setProjectDirName(directoryName)
	}
}

private fun Settings.include(projectPath: ProjectPath) {
	this.include(projectPath.toString())
}

private fun ProjectDescriptor.setProjectDirName(directoryName: String) {
	this.projectDir = this.projectDir.resolveSibling(directoryName)
}
